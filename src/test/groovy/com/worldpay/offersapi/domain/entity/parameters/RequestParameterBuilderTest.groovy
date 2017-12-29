package com.worldpay.offersapi.domain.entity.parameters

import spock.lang.Specification

class RequestParameterBuilderTest extends Specification {

    def requestParameterBuilder = new RequestParameterBuilder()

    def "RequestParameterBuilder - should allow to construct RequestParameter object"() {
        setup:
        def merchantId = "1001"
        def offerId = "1"
        def description = "description"
        def currency = "CUR"
        def amount = "1.22"
        def expiryTime = "1000000"

        when:
        requestParameterBuilder.setMerchantId(merchantId)
        requestParameterBuilder.setOfferId(offerId)
        requestParameterBuilder.setDescription(description)
        requestParameterBuilder.setCurrency(currency)
        requestParameterBuilder.setPrice(amount)
        requestParameterBuilder.setExpiryTime(expiryTime)

        def requestParameter = requestParameterBuilder.build()

        then:
        requestParameter.merchantId == merchantId
        requestParameter.offerId.get() == offerId
        requestParameter.description.get() == description
        requestParameter.currency.get() == currency
        requestParameter.price.get() == amount
        requestParameter.expiryTime.get() == expiryTime

    }

    def "RequestParameterBuilder - should throw an exception when try to pass invalid arguments"() {
        setup:
        def merchantId = ""
        def offerId = null
        def description = ""
        def currency = null
        def amount = null
        def expiryTime = ""

        when:
        requestParameterBuilder.setMerchantId(merchantId)
        requestParameterBuilder.setOfferId(offerId)
        requestParameterBuilder.setDescription(description)
        requestParameterBuilder.setCurrency(currency)
        requestParameterBuilder.setPrice(amount)
        requestParameterBuilder.setExpiryTime(expiryTime)

        then:
        thrown(IllegalArgumentException)
    }
}
