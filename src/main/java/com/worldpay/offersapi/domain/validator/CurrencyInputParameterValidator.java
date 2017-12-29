package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

public class CurrencyInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        if (!requestParameter
            .getCurrency()
            .isPresent()) {
            throw new IllegalStateException("currency is required.");
        }

        return true;
    }
}
