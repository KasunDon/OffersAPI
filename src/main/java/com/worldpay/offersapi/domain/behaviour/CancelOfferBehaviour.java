package com.worldpay.offersapi.domain.behaviour;

import com.worldpay.offersapi.domain.entity.Offer;
import com.worldpay.offersapi.domain.entity.OfferStatus;
import com.worldpay.offersapi.domain.persistence.OfferDataFetcher;
import com.worldpay.offersapi.domain.persistence.OfferDataUpdater;

import java.util.Optional;

public class CancelOfferBehaviour {

    private OfferDataFetcher offerDataFetcher;
    private OfferDataUpdater offerDataUpdater;

    public CancelOfferBehaviour(
        OfferDataFetcher offerDataFetcher,
        OfferDataUpdater offerDataUpdater
    ) {
        this.offerDataFetcher = offerDataFetcher;
        this.offerDataUpdater = offerDataUpdater;
    }

    public void cancelOffer(
        int merchantId,
        int offerId
    ) {
        Optional<Offer> optionalOffer =
            offerDataFetcher
                .fetch(merchantId, offerId);

        if (optionalOffer
            .isPresent()) {

            Offer offer =
                optionalOffer
                    .get();

            offer.setOfferStatus(OfferStatus.CANCELLED);

            offerDataUpdater
                .update(
                    merchantId,
                    offerId,
                    offer
                );
        }
    }
}
