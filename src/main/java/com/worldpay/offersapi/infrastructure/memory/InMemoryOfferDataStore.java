package com.worldpay.offersapi.infrastructure.memory;

import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.entity.OfferStatus;
import com.worldpay.offersapi.domain.persistence.OfferDataFetcher;
import com.worldpay.offersapi.domain.persistence.OfferDataPersister;
import com.worldpay.offersapi.domain.persistence.OfferDataUpdater;
import com.worldpay.offersapi.domain.persistence.OfferIdGenerator;
import com.worldpay.offersapi.library.ThreadSleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class InMemoryOfferDataStore
    implements OfferIdGenerator, OfferDataFetcher, OfferDataPersister, OfferDataUpdater {

    private final static Logger LOGGER = LoggerFactory.getLogger(InMemoryOfferDataStore.class);

    private final Map<Integer, Map<Integer, Offer>> offerStore = new LinkedHashMap<>();
    private final AtomicBoolean isBackgroundExpiryCheckProcessRunning = new AtomicBoolean(false);

    private ThreadSleeper threadSleeper;
    private int expiryCheckRefreshIntervalMs;

    public InMemoryOfferDataStore(
        ThreadSleeper threadSleeper,
        int expiryCheckRefreshIntervalMs
    ) {
        this.threadSleeper = threadSleeper;
        this.expiryCheckRefreshIntervalMs = expiryCheckRefreshIntervalMs;
    }

    public int generate(
        int merchantId
    ) {
        return
            offerStore.containsKey(merchantId) ?
                offerStore.get(merchantId).size() + 1 : 1;
    }

    public Optional<Offer> fetch(
        int merchantId,
        int offerId
    ) {
        if (offerStore
            .containsKey(merchantId)) {

            Map<Integer, Offer> offerMap =
                offerStore
                    .get(merchantId);

            if (offerMap
                .containsKey(offerId)) {

                return
                    Optional.of(
                        offerMap
                            .get(offerId)
                    );
            }

        }

        return Optional.empty();
    }

    public void persist(
        Offer offer
    ) {
        Map<Integer, Offer> offerMap = new HashMap<>();

        offerMap.put(offer.getId(), offer);

        offerStore
            .computeIfAbsent(
                offer.getMerchant().getId(),
                x -> new HashMap<>()
            )
            .putAll(offerMap);

        LOGGER.info("@@ New offer created. offerId - " + offer.getId());

        tryStartBackgroundCleanupProcess();
    }

    private void tryStartBackgroundCleanupProcess() {

        if (isBackgroundExpiryCheckProcessRunning
            .getAndSet(true)) {
            return;
        }

        new Thread(() -> {

            LOGGER.info("@@ Background expiry check process is running ...");

            while (true) {

                offerStore
                    .entrySet()
                    .forEach(merchant -> {
                        merchant
                            .getValue()
                            .entrySet()
                            .stream()
                            .filter(offer -> offer.getValue().getOfferStatus() != OfferStatus.EXPIRED)
                            .filter(offer -> offer.getValue().getOfferStatus() != OfferStatus.CANCELLED)
                            .filter(offer ->
                                offer
                                    .getValue()
                                    .getExpiryTime()
                                    .isBefore(Instant.now())
                            )
                            .forEach(offer -> {
                                    Offer iteratingOffer = offer.getValue();
                                    iteratingOffer.setOfferStatus(OfferStatus.EXPIRED);
                                    LOGGER.info("@@ Offer set to expired. offerId - " + iteratingOffer.getId());
                                }
                            );
                    });

                threadSleeper
                    .sleep(expiryCheckRefreshIntervalMs);
            }

        }).start();
    }

    public void update(
        int merchantId,
        int offerId,
        Offer offer
    ) {
        if (offerStore
            .containsKey(merchantId)) {

            Map<Integer, Offer> offerMap = offerStore.get(merchantId);

            if (offerMap
                .containsKey(offerId)) {

                LOGGER.info("@@ Updating data for offerId - " + offerId);

                offerMap.put(offerId, offer);
            }
        }
    }
}
