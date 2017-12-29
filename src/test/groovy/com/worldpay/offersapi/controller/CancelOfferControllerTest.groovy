package com.worldpay.offersapi.controller

import com.worldpay.offersapi.domain.behaviour.CancelOfferBehaviour
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper
import com.worldpay.offersapi.domain.validator.InputParameterValidator
import spock.lang.Specification

class CancelOfferControllerTest extends Specification {

    def inputParameterValidator = Mock(InputParameterValidator)
    def cancelOfferBehaviour = Mock(CancelOfferBehaviour)
    def requestParameterToFieldParameterMapper = Mock(RequestParameterToFieldParameterMapper)

    def cancelOfferController = new CancelOfferController(
        inputParameterValidator,
        cancelOfferBehaviour,
        requestParameterToFieldParameterMapper
    )

    def "CancelOfferController - should allow an offer to be canceled"() {

        setup:
        def requestParameter = Mock(RequestParameter)
        def fieldParameter = Mock(FieldParameter) {
            getMerchantId() >> 1001
            getOfferId() >> Optional.of(1)
        }

        when:
        def outputMap = cancelOfferController.cancelOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter)
        1 * requestParameterToFieldParameterMapper.map(requestParameter) >> fieldParameter
        1 * cancelOfferBehaviour.cancelOffer(_, _)

        outputMap.get("message") == "offerId :- 1 has been canceled."
    }

    def "CancelOfferController - should not allow an offer to be canceled when initial validation fails"() {

        setup:
        def requestParameter = Mock(RequestParameter)

        when:
        cancelOfferController.cancelOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter) >> {
            throw new IllegalStateException()
        }

        thrown(IllegalStateException)

        0 * requestParameterToFieldParameterMapper.map(requestParameter)
        0 * cancelOfferBehaviour.cancelOffer(_, _)
    }
}
