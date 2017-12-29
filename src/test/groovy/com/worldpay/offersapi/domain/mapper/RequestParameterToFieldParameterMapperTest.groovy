package com.worldpay.offersapi.domain.mapper

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

import java.time.Instant

class RequestParameterToFieldParameterMapperTest extends Specification {

    def requestParameterToFieldParameterMapper = new RequestParameterToFieldParameterMapper()

    def "RequestParameterToFieldParameterMapper - should map bounded parameters in RequestParameter object to FieldParameter object"() {

        setup:
        def requestParameter = Mock(RequestParameter) {
            getMerchantId() >> "1001"
            getOfferId() >> Optional.of("1")
            getDescription() >> Optional.of("DESCRIPTION")
            getCurrency() >> Optional.of("CUR")
            getPrice() >> Optional.of("1.23")
            getExpiryTime() >> Optional.of("100000")
        }

        when:
        def fieldParameter = requestParameterToFieldParameterMapper.map(requestParameter)

        then:
        fieldParameter.merchantId == 1001

        fieldParameter.offerId instanceof Optional
        fieldParameter.offerId.get() == 1

        fieldParameter.description instanceof Optional
        fieldParameter.description.get() == "DESCRIPTION"

        fieldParameter.currency instanceof Optional
        fieldParameter.currency.get() == "CUR"

        fieldParameter.price instanceof Optional
        fieldParameter.price.get() == BigDecimal.valueOf(1.23)

        fieldParameter.expiryTime instanceof Optional
        fieldParameter.expiryTime.get() == Instant.ofEpochSecond(100000)
    }

    def "RequestParameterToFieldParameterMapper - should map empty parameters in RequestParameter object to FieldParameter object"() {

        setup:
        def requestParameter = Mock(RequestParameter) {
            getMerchantId() >> "1001"
            getOfferId() >> Optional.empty()
            getDescription() >> Optional.empty()
            getCurrency() >> Optional.empty()
            getPrice() >> Optional.empty()
            getExpiryTime() >> Optional.empty()
        }

        when:
        def fieldParameter = requestParameterToFieldParameterMapper.map(requestParameter)

        then:
        fieldParameter.merchantId == 1001

        fieldParameter.offerId instanceof Optional
        fieldParameter.offerId.isPresent() == false

        fieldParameter.description instanceof Optional
        fieldParameter.description.isPresent() == false

        fieldParameter.currency instanceof Optional
        fieldParameter.currency.isPresent() == false

        fieldParameter.price instanceof Optional
        fieldParameter.price.isPresent() == false

        fieldParameter.expiryTime instanceof Optional
        fieldParameter.expiryTime.isPresent() == false
    }

}
