package com.worldpay.offersapi.domain.entity.parameters;

import java.util.Optional;

public class RequestParameterBuilder {

    private String merchantId;
    private String offerId;
    private String description;
    private String currency;
    private String price;
    private String expiryTime;

    public static RequestParameterBuilder getInstance() {
        return new RequestParameterBuilder();
    }

    public RequestParameterBuilder setMerchantId(
        String merchantId
    ) {

        if (merchantId == null || merchantId.isEmpty()) {
            throw new IllegalArgumentException("merchantId cannot be null or empty.");
        }

        this.merchantId = merchantId;

        return this;
    }

    public RequestParameterBuilder setOfferId(
        String offerId
    ) {

        if (offerId == null || offerId.isEmpty()) {
            throw new IllegalArgumentException("offerId cannot be null or empty.");
        }

        this.offerId = offerId;

        return this;
    }

    public RequestParameterBuilder setDescription(
        String description
    ) {

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description cannot be null or empty.");
        }

        this.description = description;

        return this;
    }

    public RequestParameterBuilder setCurrency(
        String currency
    ) {

        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("currency cannot be null or empty.");
        }

        this.currency = currency;

        return this;
    }

    public RequestParameterBuilder setPrice(
        String price
    ) {

        if (price == null || price.isEmpty()) {
            throw new IllegalArgumentException("price cannot be null or empty.");
        }

        this.price = price;

        return this;
    }

    public RequestParameterBuilder setExpiryTime(
        String expiryTime
    ) {

        if (expiryTime == null || expiryTime.isEmpty()) {
            throw new IllegalArgumentException("expiryTime cannot be null or empty.");
        }

        this.expiryTime = expiryTime;

        return this;
    }

    public RequestParameter build() {

        return new RequestParameter(
            merchantId,
            Optional.ofNullable(offerId),
            Optional.ofNullable(description),
            Optional.ofNullable(currency),
            Optional.ofNullable(price),
            Optional.ofNullable(expiryTime)
        );
    }
}
