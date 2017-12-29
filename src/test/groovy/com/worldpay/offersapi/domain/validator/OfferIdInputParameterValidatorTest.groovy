package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class OfferIdInputParameterValidatorTest extends Specification {

    def offerIdInputParameterValidator = new OfferIdInputParameterValidator()

    def "OfferIdInputParameterValidator - should validate given offerId in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getOfferId() >> Optional.of("1")
        }

        when:
        def isValid = offerIdInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "OfferIdInputParameterValidator - should throw an exception when offerId is empty in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getOfferId() >> Optional.empty()
        }

        when:
        offerIdInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }

    def "OfferIdInputParameterValidator - should throw an exception when given non-numeric offerId in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getOfferId() >> Optional.of("1sdas5")
        }

        when:
        offerIdInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }
}
