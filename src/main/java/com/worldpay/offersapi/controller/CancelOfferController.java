package com.worldpay.offersapi.controller;

import com.worldpay.offersapi.domain.behaviour.CancelOfferBehaviour;
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper;
import com.worldpay.offersapi.domain.validator.InputParameterValidator;

import java.util.Map;

import static com.worldpay.offersapi.library.OutputMapForJson.output;

public class CancelOfferController {

    private InputParameterValidator inputParameterValidator;
    private CancelOfferBehaviour cancelOfferBehaviour;
    private RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper;

    public CancelOfferController(
        InputParameterValidator inputParameterValidator,
        CancelOfferBehaviour cancelOfferBehaviour,
        RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper
    ) {
        this.inputParameterValidator = inputParameterValidator;
        this.cancelOfferBehaviour = cancelOfferBehaviour;
        this.requestParameterToFieldParameterMapper = requestParameterToFieldParameterMapper;
    }

    public Map<String, String> cancelOfferAction(
        RequestParameter requestParameter
    ) {
        inputParameterValidator
            .validate(requestParameter);

        FieldParameter fieldParameter =
            requestParameterToFieldParameterMapper
                .map(requestParameter);

        cancelOfferBehaviour
            .cancelOffer(
                fieldParameter.getMerchantId(),
                fieldParameter.getOfferId().get()
            );

        return
            output(
                "message",
                "offerId :- " + fieldParameter.getOfferId().get() + " has been canceled."
            );
    }
}
