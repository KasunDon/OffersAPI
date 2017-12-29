package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import org.apache.commons.lang.StringUtils;

public class OfferIdInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        if (!requestParameter
            .getOfferId()
            .isPresent()) {
            throw new IllegalStateException("offerId is required.");
        }

        String offerId =
            requestParameter
                .getOfferId()
                .get();

        if (offerId == null) {
            throw new IllegalStateException("offerId cannot be null.");
        }

        if (!StringUtils.isNumeric(offerId)) {
            throw new IllegalStateException("offerId should be numeric.");
        }


        return true;
    }
}
