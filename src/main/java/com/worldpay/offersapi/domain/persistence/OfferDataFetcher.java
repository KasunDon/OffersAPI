package com.worldpay.offersapi.domain.persistence;

import com.worldpay.offersapi.domain.entity.Offer;

import java.util.Optional;

public interface OfferDataFetcher {

    Optional<Offer> fetch(
        int merchantId,
        int offerId
    );
}
