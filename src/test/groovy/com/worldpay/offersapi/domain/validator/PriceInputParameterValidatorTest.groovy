package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class PriceInputParameterValidatorTest extends Specification {

    def priceInputParameterValidator = new PriceInputParameterValidator()

    def "PriceInputParameterValidator - should validate given price in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getPrice() >> Optional.of("1.23")
        }

        when:
        def isValid = priceInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "PriceInputParameterValidator - should throw an exception when missing price in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getPrice() >> Optional.empty()
        }

        when:
        priceInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }

    def "PriceInputParameterValidator - should throw an exception when given non-numeric price in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getPrice() >> Optional.of("1we5")
        }

        when:
        priceInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }
}
