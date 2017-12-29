package com.worldpay.offersapi.domain.mapper;

import com.worldpay.offersapi.domain.entity.parameters.FieldParameter;
import com.worldpay.offersapi.domain.entity.parameters.RequestParameter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class RequestParameterToFieldParameterMapper {

    public FieldParameter map(
        RequestParameter requestParameter
    ) {

        int merchantId = Integer.valueOf(requestParameter.getMerchantId());

        Optional<Integer> offerId =
            requestParameter.getOfferId().isPresent() ?
                Optional.of(Integer.valueOf(requestParameter.getOfferId().get())) :
                Optional.empty();

        Optional<String> description =
            requestParameter.getDescription().isPresent() ?
                Optional.of(requestParameter.getDescription().get()) :
                Optional.empty();

        Optional<String> currency =
            requestParameter.getCurrency().isPresent() ?
                Optional.of(requestParameter.getCurrency().get()) :
                Optional.empty();

        Optional<BigDecimal> price =
            requestParameter.getPrice().isPresent() ?
                Optional.of(BigDecimal.valueOf(Double.valueOf(requestParameter.getPrice().get()))) :
                Optional.empty();

        Optional<Instant> expiryTime =
            requestParameter.getExpiryTime().isPresent() ?
                Optional.of(Instant.ofEpochSecond(Long.valueOf(requestParameter.getExpiryTime().get()))) :
                Optional.empty();

        return new FieldParameter(
            merchantId,
            offerId,
            description,
            currency,
            price,
            expiryTime
        );
    }
}
