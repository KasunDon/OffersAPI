package com.worldpay.offersapi.controller;

import com.worldpay.offersapi.domain.behaviour.DisplayOfferBehaviour;
import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.entity.parameters.FieldParameter;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import com.worldpay.offersapi.domain.mapper.RequestParameterToFieldParameterMapper;
import com.worldpay.offersapi.domain.validator.InputParameterValidator;

import java.util.Map;
import java.util.Optional;

import static com.worldpay.offersapi.library.OutputMapForJson.output;

public class DisplayOfferController {

    private InputParameterValidator inputParameterValidator;
    private DisplayOfferBehaviour displayOfferBehaviour;
    private RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper;

    public DisplayOfferController(
        InputParameterValidator inputParameterValidator,
        DisplayOfferBehaviour displayOfferBehaviour,
        RequestParameterToFieldParameterMapper requestParameterToFieldParameterMapper
    ) {
        this.inputParameterValidator = inputParameterValidator;
        this.displayOfferBehaviour = displayOfferBehaviour;
        this.requestParameterToFieldParameterMapper = requestParameterToFieldParameterMapper;
    }

    public Map<String, Object> displayOfferAction(
        RequestParameter requestParameter
    ) {
        inputParameterValidator
            .validate(requestParameter);

        FieldParameter fieldParameter =
            requestParameterToFieldParameterMapper
                .map(requestParameter);

        Optional<Offer> offer =
            displayOfferBehaviour
                .displayOffer(
                    fieldParameter.getMerchantId(),
                    fieldParameter.getOfferId().get()
                );

        if (!offer
            .isPresent()) {

            return output("message", "No offer found.");
        }

        return output("offer", offer.get());
    }
}
