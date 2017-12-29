package com.worldpay.offersapi.domain.behaviour;

import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.entity.OfferInitializingFactory;
import com.worldpay.offersapi.domain.persistence.OfferDataPersister;
import com.worldpay.offersapi.domain.persistence.OfferIdGenerator;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateOfferBehaviour {

    private OfferDataPersister offerDataPersister;
    private OfferIdGenerator offerIdGenerator;
    private OfferInitializingFactory offerInitializingFactory;

    public CreateOfferBehaviour(
        OfferDataPersister offerDataPersister,
        OfferIdGenerator offerIdGenerator,
        OfferInitializingFactory offerInitializingFactory
    ) {
        this.offerDataPersister = offerDataPersister;
        this.offerIdGenerator = offerIdGenerator;
        this.offerInitializingFactory = offerInitializingFactory;
    }

    public Offer createOffer(
        int merchantId,
        String description,
        String currency,
        BigDecimal price,
        Instant expiryTime
    ) {
        int offerId =
            offerIdGenerator
                .generate(merchantId);

        Offer offer =
            offerInitializingFactory.create(
                merchantId,
                offerId,
                description,
                currency,
                price,
                expiryTime
            );

        offerDataPersister.persist(offer);

        return offer;
    }
}
