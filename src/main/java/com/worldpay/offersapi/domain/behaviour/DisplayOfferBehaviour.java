package com.worldpay.offersapi.domain.behaviour;

import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.persistence.OfferDataFetcher;

import java.util.Optional;

public class DisplayOfferBehaviour {

    private OfferDataFetcher offerDataFetcher;

    public DisplayOfferBehaviour(
        OfferDataFetcher offerDataFetcher
    ) {
        this.offerDataFetcher = offerDataFetcher;
    }

    public Optional<Offer> displayOffer(
        int merchantId,
        int offerId
    ) {
        return
            offerDataFetcher
                .fetch(
                    merchantId,
                    offerId
                );
    }
}
