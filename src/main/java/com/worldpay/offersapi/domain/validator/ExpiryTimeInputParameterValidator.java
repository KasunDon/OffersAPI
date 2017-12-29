package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

public class ExpiryTimeInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        if (!requestParameter
            .getExpiryTime()
            .isPresent()) {
            throw new IllegalStateException("expiryTime is required.");
        }

        return true;
    }
}
