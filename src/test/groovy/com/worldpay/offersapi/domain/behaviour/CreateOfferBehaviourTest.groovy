package com.worldpay.offersapi.domain.behaviour

import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.entity.OfferInitializingFactory
import com.worldpay.offersapi.domain.persistence.OfferDataPersister
import com.worldpay.offersapi.domain.persistence.OfferIdGenerator
import spock.lang.Specification

import java.time.Instant

class CreateOfferBehaviourTest extends Specification {

    def offerDataPersister = Mock(OfferDataPersister)
    def offerIdGenerator = Mock(OfferIdGenerator)
    def offerInitializingFactory = Mock(OfferInitializingFactory)

    def createOfferBehaviour = new CreateOfferBehaviour(
        offerDataPersister,
        offerIdGenerator,
        offerInitializingFactory
    )

    def "CreateOfferBehaviour - should allow to create an offer"() {
        setup:
        def merchantId = 1001
        def description = "description"
        def currency = "CUR"
        def price = BigDecimal.valueOf(1.00)
        def expiryTime = Instant.MAX

        def offer = Mock(Offer) {
            getId() >> 1
            getDescription() >> description
        }

        when:
        def createdOffer = createOfferBehaviour.createOffer(merchantId, description, currency, price, expiryTime)

        then:
        1 * offerIdGenerator.generate(_) >> 1
        1 * offerInitializingFactory.create(_, _, _, _, _, _) >> offer
        1 * offerDataPersister.persist(offer)

        createdOffer.id == 1
        createdOffer.description == description
    }
}
