package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

public interface InputParameterValidator {

    boolean validate(
        RequestParameter requestParameter
    );
}
