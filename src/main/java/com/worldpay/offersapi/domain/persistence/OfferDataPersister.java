package com.worldpay.offersapi.domain.persistence;

import com.worldpay.offersapi.domain.entity.Offer;

public interface OfferDataPersister {

    void persist(
        Offer offer
    );
}
