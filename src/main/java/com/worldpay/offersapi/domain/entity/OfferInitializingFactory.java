package com.worldpay.offersapi.domain.entity;

import java.math.BigDecimal;
import java.time.Instant;

public class OfferInitializingFactory {

    public Offer create(
        int merchantId,
        int offerId,
        String description,
        String currency,
        BigDecimal amount,
        Instant expiryTime
    ) {

        Merchant merchant = new Merchant(merchantId);
        Price price = new Price(currency, amount);

        return new Offer(
            offerId,
            merchant,
            description,
            OfferStatus.ACTIVE,
            price,
            expiryTime
        );

    }
}
