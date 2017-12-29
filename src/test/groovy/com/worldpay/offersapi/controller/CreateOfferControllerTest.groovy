package com.worldpay.offersapi.controller

import com.worldpay.offersapi.domain.behaviour.CreateOfferBehaviour
import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper
import com.worldpay.offersapi.domain.validator.InputParameterValidator
import spock.lang.Specification

import java.time.Instant

class CreateOfferControllerTest extends Specification {

    def inputParameterValidator = Mock(InputParameterValidator)
    def createOfferBehaviour = Mock(CreateOfferBehaviour)
    def requestParameterToFieldParameterMapper = Mock(RequestParameterToFieldParameterMapper)

    def createOfferController = new CreateOfferController(
        inputParameterValidator,
        createOfferBehaviour,
        requestParameterToFieldParameterMapper
    )

    def "CreateOfferController - should allow an offer to be created"() {

        setup:
        def requestParameter = Mock(RequestParameter)
        def fieldParameter = Mock(FieldParameter) {
            getMerchantId() >> 1001
            getDescription() >> Optional.of("description")
            getCurrency() >> Optional.of("GBP")
            getPrice() >> Optional.of(BigDecimal.ONE)
            getExpiryTime() >> Optional.of(Instant.MIN)
        }

        def offer = Mock(Offer) {
            getId() >> 1
        }

        when:
        def outputMap = createOfferController.createOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter)
        1 * requestParameterToFieldParameterMapper.map(requestParameter) >> fieldParameter
        1 * createOfferBehaviour.createOffer(_, _, _, _, _) >> offer

        outputMap.get("message") == "offer created - 1"
    }

    def "CreateOfferController - should not allow an offer to be created when validation fails"() {

        setup:
        def requestParameter = Mock(RequestParameter)

        when:
        createOfferController.createOfferAction(requestParameter)

        then:
        1 * inputParameterValidator.validate(requestParameter) >> {
            throw new IllegalStateException()
        }

        thrown(IllegalStateException)

        0 * requestParameterToFieldParameterMapper.map(requestParameter)
        0 * createOfferBehaviour.createOffer(_, _, _, _, _)
    }
}
