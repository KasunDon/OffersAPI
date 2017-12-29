package com.worldpay.offersapi.domain.entity.parameters;

import java.util.Optional;

public class RequestParameter {

    private String merchantId;
    private Optional<String> offerId;
    private Optional<String> description;
    private Optional<String> currency;
    private Optional<String> price;
    private Optional<String> expiryTime;

    public RequestParameter(
        String merchantId,
        Optional<String> offerId,
        Optional<String> description,
        Optional<String> currency,
        Optional<String> price,
        Optional<String> expiryTime
    ) {
        this.merchantId = merchantId;
        this.offerId = offerId;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.expiryTime = expiryTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public Optional<String> getOfferId() {
        return offerId;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getCurrency() {
        return currency;
    }

    public Optional<String> getPrice() {
        return price;
    }

    public Optional<String> getExpiryTime() {
        return expiryTime;
    }
}
