package com.worldpay.offersapi.controller;

import com.worldpay.offersapi.domain.behaviour.CreateOfferBehaviour;
import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper;
import com.worldpay.offersapi.domain.validator.InputParameterValidator;

import java.util.Map;

import static com.worldpay.offersapi.library.OutputMapForJson.output;

public class CreateOfferController {

    private InputParameterValidator inputParameterValidator;
    private CreateOfferBehaviour createOfferBehaviour;
    private RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper;

    public CreateOfferController(
        InputParameterValidator inputParameterValidator,
        CreateOfferBehaviour createOfferBehaviour,
        RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper
    ) {
        this.inputParameterValidator = inputParameterValidator;
        this.createOfferBehaviour = createOfferBehaviour;
        this.requestParameterToFieldParameterMapper = requestParameterToFieldParameterMapper;
    }

    public Map<String, String> createOfferAction(
        RequestParameter requestParameter
    ) {
        inputParameterValidator
            .validate(requestParameter);

        FieldParameter fieldParameter =
            requestParameterToFieldParameterMapper
                .map(requestParameter);

        Offer offer = createOfferBehaviour
            .createOffer(
                fieldParameter.getMerchantId(),
                fieldParameter.getDescription().get(),
                fieldParameter.getCurrency().get(),
                fieldParameter.getPrice().get(),
                fieldParameter.getExpiryTime().get()
            );

        return
            output(
                "message",
                "offer created - " + offer.getId()
            );
    }
}
