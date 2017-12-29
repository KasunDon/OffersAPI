package com.worldpay.offersapi.domain.entity.parameters;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class FieldParameter {

    private int merchantId;
    private Optional<Integer> offerId;
    private Optional<String> description;
    private Optional<String> currency;
    private Optional<BigDecimal> price;
    private Optional<Instant> expiryTime;

    public FieldParameter(
        int merchantId,
        Optional<Integer> offerId,
        Optional<String> description,
        Optional<String> currency,
        Optional<BigDecimal> price,
        Optional<Instant> expiryTime
    ) {
        this.merchantId = merchantId;
        this.offerId = offerId;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.expiryTime = expiryTime;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public Optional<Integer> getOfferId() {
        return offerId;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getCurrency() {
        return currency;
    }

    public Optional<BigDecimal> getPrice() {
        return price;
    }

    public Optional<Instant> getExpiryTime() {
        return expiryTime;
    }
}
