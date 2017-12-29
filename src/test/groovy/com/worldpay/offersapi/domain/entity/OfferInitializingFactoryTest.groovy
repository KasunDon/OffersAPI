package com.worldpay.offersapi.domain.entity

import spock.lang.Specification

import java.time.Instant

class OfferInitializingFactoryTest extends Specification {

    def offerInitializingFactory = new OfferInitializingFactory()

    def "OfferInitializingFactory - allows to initialize an offer with bounded parameters"() {
        setup:
        def merchantId = 1001
        def offerId = 1
        def description = "description"
        def currency = "CUR"
        def amount = BigDecimal.ONE
        def expiryTime = Instant.MAX

        when:
        def createdOffer = offerInitializingFactory.create(merchantId, offerId, description, currency, amount, expiryTime)

        then:
        createdOffer instanceof Offer

        createdOffer.id == offerId
        createdOffer.merchant.id == merchantId
        createdOffer.description == description
        createdOffer.price.currency == currency
        createdOffer.price.amount == amount
        createdOffer.expiryTime == expiryTime
        createdOffer.offerStatus == OfferStatus.ACTIVE
    }
}
