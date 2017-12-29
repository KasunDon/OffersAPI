package com.worldpay.offersapi.infrastructure.memory

import com.worldpay.offersapi.domain.entity.Merchant
import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.entity.OfferStatus
import com.worldpay.offersapi.domain.entity.Price
import com.worldpay.offersapi.library.ThreadSleeper
import spock.lang.Specification

import java.time.Instant

class InMemoryOfferDataStoreTest extends Specification {

    def threadSleeper = Mock(ThreadSleeper)
    def expiryCheckRefereshIntervalMs = 10000

    def inMemoryOfferDataStore = new InMemoryOfferDataStore(
        threadSleeper,
        expiryCheckRefereshIntervalMs
    )

    def "InMemoryOfferDataStore - should be able to generate offerId for given merchant"() {
        setup:
        def merchantId = 1001

        when:
        def generatedOfferId = inMemoryOfferDataStore.generate(merchantId)

        then:
        generatedOfferId == 1
    }

    def "InMemoryOfferDataStore - should be able to persist offer"() {
        setup:
        def merchantId = 1001
        def offerId = 10

        def offer = new Offer(
            offerId,
            Mock(Merchant) {
                getId() >> merchantId
            },
            "description",
            OfferStatus.ACTIVE,
            new Price("CUR", BigDecimal.ONE),
            Instant.MIN
        )

        when:
        inMemoryOfferDataStore.persist(offer)
        def fetchedOffer = inMemoryOfferDataStore.fetch(merchantId, offerId)

        then:

        fetchedOffer instanceof Optional

        fetchedOffer.get().id == offerId
        fetchedOffer.get().merchant.id == 1001
        fetchedOffer.get().expiryTime == Instant.MIN
    }

    def "InMemoryOfferDataStore - should be able to fetch offerId which not exists"() {
        setup:
        def merchantId = 1001
        def offerId = 100

        def offer = new Offer(
            offerId,
            Mock(Merchant) {
                getId() >> merchantId
            },
            "description",
            OfferStatus.ACTIVE,
            new Price("CUR", BigDecimal.ONE),
            Instant.MIN
        )

        inMemoryOfferDataStore.persist(offer)

        when:
        def fetchedOffer = inMemoryOfferDataStore.fetch(merchantId, 10)

        then:

        fetchedOffer instanceof Optional

        fetchedOffer.isPresent() == false
    }

    def "InMemoryOfferDataStore - should be able to fetch offerId"() {
        setup:
        def merchantId = 1001
        def offerId = 100

        def offer = new Offer(
            offerId,
            Mock(Merchant) {
                getId() >> merchantId
            },
            "description",
            OfferStatus.ACTIVE,
            new Price("CUR", BigDecimal.ONE),
            Instant.MIN
        )

        inMemoryOfferDataStore.persist(offer)

        when:
        def fetchedOffer = inMemoryOfferDataStore.fetch(merchantId, offerId)

        then:

        fetchedOffer instanceof Optional

        fetchedOffer.get().id == offerId
        fetchedOffer.get().merchant.id == merchantId
    }

    def "InMemoryOfferDataStore - should be able to update offer using merchantId and offerId"() {
        setup:
        def merchantId = 1001
        def offerId = 100

        def offer = new Offer(
            offerId,
            Mock(Merchant) {
                getId() >> merchantId
            },
            "description",
            OfferStatus.ACTIVE,
            new Price("CUR", BigDecimal.ONE),
            Instant.MAX
        )

        inMemoryOfferDataStore.persist(offer)

        when:

        offer.setOfferStatus(OfferStatus.CANCELLED)

        inMemoryOfferDataStore.update(merchantId, offerId, offer)

        def fetchedOffer = inMemoryOfferDataStore.fetch(merchantId, offerId)

        then:

        fetchedOffer instanceof Optional

        fetchedOffer.get().id == offerId
        fetchedOffer.get().merchant.id == merchantId
        fetchedOffer.get().offerStatus == OfferStatus.CANCELLED
    }
}
