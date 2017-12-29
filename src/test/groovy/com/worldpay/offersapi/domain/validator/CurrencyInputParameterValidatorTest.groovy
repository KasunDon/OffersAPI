package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class CurrencyInputParameterValidatorTest extends Specification {

    def currencyInputParameterValidator = new CurrencyInputParameterValidator()

    def "CurrencyInputParameterValidator - should validate given currency value in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getCurrency() >> Optional.of("CUR")
        }

        when:
        def isValid = currencyInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "CurrencyInputParameterValidator - should throw an exception when missing currency in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getCurrency() >> Optional.empty()
        }

        when:
        currencyInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }
}
