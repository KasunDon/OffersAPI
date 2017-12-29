package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class ExpiryTimeInputParameterValidatorTest extends Specification {

    def expiryTimeInputParameterValidator = new ExpiryTimeInputParameterValidator()

    def "ExpiryTimeInputParameterValidator - should validate given expiryTime value in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getExpiryTime() >> Optional.of("10000000")
        }

        when:
        def isValid = expiryTimeInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "ExpiryTimeInputParameterValidator - should throw an exception when missing expiryTime in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getExpiryTime() >> Optional.empty()
        }

        when:
        expiryTimeInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }
}
