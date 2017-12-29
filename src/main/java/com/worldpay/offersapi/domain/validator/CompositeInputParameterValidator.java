package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

import java.util.List;

public class CompositeInputParameterValidator implements InputParameterValidator {

    private List<InputParameterValidator> inputParameterValidators;

    public CompositeInputParameterValidator(
        List<InputParameterValidator> inputParameterValidators
    ) {
        this.inputParameterValidators = inputParameterValidators;
    }

    public boolean validate(
        RequestParameter requestParameter
    ) {

        inputParameterValidators
            .forEach(
                validator -> validator.validate(requestParameter)
            );

        return true;
    }
}
