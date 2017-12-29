package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import org.apache.commons.lang.math.NumberUtils;

public class PriceInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        if (!requestParameter
            .getPrice()
            .isPresent()) {
            throw new IllegalStateException("price is required.");
        }

        String price =
            requestParameter
                .getPrice()
                .get();

        if (!NumberUtils.isNumber(price)) {
            throw new IllegalStateException("price should be a number.");
        }

        return true;
    }
}
