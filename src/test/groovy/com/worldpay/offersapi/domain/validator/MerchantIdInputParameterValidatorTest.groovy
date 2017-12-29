package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class MerchantIdInputParameterValidatorTest extends Specification {

    def merchantIdInputParameterValidator = new MerchantIdInputParameterValidator()

    def "MerchantIdInputParameterValidator - should validate given merchantId in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getMerchantId() >> "1001"
        }

        when:
        def isValid = merchantIdInputParameterValidator.validate(requestParameter)

        then:
        isValid == true
    }

    def "MerchantIdInputParameterValidator - should throw an exception when merchantId is null in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getMerchantId() >> null
        }

        when:
        merchantIdInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }

    def "MerchantIdInputParameterValidator - should throw an exception when given non-numeric merchantId in request parameter"() {
        setup:
        def requestParameter = Mock(RequestParameter) {
            getMerchantId() >> "1s3"
        }

        when:
        merchantIdInputParameterValidator.validate(requestParameter)

        then:
        thrown(IllegalStateException)
    }

}
