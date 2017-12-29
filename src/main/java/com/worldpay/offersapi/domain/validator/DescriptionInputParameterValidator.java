package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

public class DescriptionInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        if (!requestParameter
            .getDescription()
            .isPresent()) {
            throw new IllegalStateException("description is required.");
        }

        return true;
    }
}
