package com.worldpay.offersapi.domain.persistence;

public interface OfferIdGenerator {

    int generate(int merchantId);
}
