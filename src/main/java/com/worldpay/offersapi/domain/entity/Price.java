package com.worldpay.offersapi.domain.entity;

import java.math.BigDecimal;

public class Price {

    private String currency;
    private BigDecimal amount;

    public Price(
        String currency,
        BigDecimal amount
    ) {

        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("currency cannot be null or empty.");
        }

        if (amount == null) {
            throw new IllegalArgumentException("amount cannot be null.");
        }

        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
