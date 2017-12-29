package com.worldpay.offersapi.domain.validator;

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;
import org.apache.commons.lang.StringUtils;

public class MerchantIdInputParameterValidator implements InputParameterValidator {

    public boolean validate(
        RequestParameter requestParameter
    ) {

        String merchantId =
            requestParameter
                .getMerchantId();

        if (merchantId == null) {
            throw new IllegalStateException("merchantId cannot be null.");
        }

        if (!StringUtils.isNumeric(merchantId)) {
            throw new IllegalStateException("merchantId should be numeric.");
        }

        return true;
    }
}
