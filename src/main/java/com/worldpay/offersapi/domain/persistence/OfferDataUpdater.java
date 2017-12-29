package com.worldpay.offersapi.domain.persistence;

import com.worldpay.offersapi.domain.entity.Offer;

public interface OfferDataUpdater {

    void update(
        int merchantId,
        int offerId,
        Offer offer
    );
}
