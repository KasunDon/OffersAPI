package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class DescriptionInputParameterValidatorTest extends Specification {

    def descriptionInputParameterValidator = new DescriptionInputParameterValidator()

    def "DescriptionInputParameterValidator - should validate given description value in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getDescription() >> Optional.of("DESCRIPTION")
        }

        when:
        def isValid = descriptionInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "DescriptionInputParameterValidator - should throw an exception when missing description in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getDescription() >> Optional.empty()
        }

        when:
        descriptionInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }
}
