package com.worldpay.offersapi.controller

import com.worldpay.offersapi.domain.behaviour.DisplayOfferBehaviour
import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper
import com.worldpay.offersapi.domain.validator.InputParameterValidator
import spock.lang.Specification

class DisplayOfferControllerTest extends Specification {

    def inputParameterValidator = Mock(InputParameterValidator)
    def displayOfferBehaviour = Mock(DisplayOfferBehaviour)
    def requestParameterToFieldParameterMapper = Mock(RequestParameterToFieldParameterMapper)

    def displayOfferController = new DisplayOfferController(
        inputParameterValidator,
        displayOfferBehaviour,
        requestParameterToFieldParameterMapper
    )

    def "DisplayOfferController - should allow an offer to be displayed"() {

        setup:
        def requestParameter = Mock(RequestParameter)
        def fieldParameter = Mock(FieldParameter) {
            getMerchantId() >> 1001
            getOfferId() >> Optional.of(1)
        }

        def offer = Mock(Offer) {
            getId() >> 1
            getDescription() >> "description"
        }

        when:
        def outputMap = displayOfferController.displayOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter)
        1 * requestParameterToFieldParameterMapper.map(requestParameter) >> fieldParameter
        1 * displayOfferBehaviour.displayOffer(_, _) >> Optional.of(offer)

        outputMap.get("offer") == offer

        offer.id == 1
        offer.description == "description"
    }

    def "DisplayOfferController - should an message when no offer found to be displayed"() {

        setup:
        def requestParameter = Mock(RequestParameter)
        def fieldParameter = Mock(FieldParameter) {
            getMerchantId() >> 1001
            getOfferId() >> Optional.of(1)
        }

        when:
        def outputMap = displayOfferController.displayOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter)
        1 * requestParameterToFieldParameterMapper.map(requestParameter) >> fieldParameter
        1 * displayOfferBehaviour.displayOffer(_, _) >> Optional.empty()

        outputMap.get("message") == "No offer found."
    }

    def "DisplayOfferController - should not allow an offer to be displayed when validation fails"() {

        setup:
        def requestParameter = Mock(RequestParameter)

        when:
        displayOfferController.displayOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter) >> {
            throw new IllegalStateException()
        }

        thrown(IllegalStateException)

        0 * requestParameterToFieldParameterMapper.map(requestParameter)
        0 * displayOfferBehaviour.displayOffer(_, _)
    }
}
