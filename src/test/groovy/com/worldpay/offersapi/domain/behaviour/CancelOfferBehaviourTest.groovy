package com.worldpay.offersapi.domain.behaviour

import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.entity.OfferStatus
import com.worldpay.offersapi.domain.persistence.OfferDataFetcher
import com.worldpay.offersapi.domain.persistence.OfferDataUpdater
import spock.lang.Specification

class CancelOfferBehaviourTest extends Specification {

    def offerDataFetcher = Mock(OfferDataFetcher)
    def offerDataUpdater = Mock(OfferDataUpdater)

    def cancelOfferBehaviour = new CancelOfferBehaviour(
        offerDataFetcher,
        offerDataUpdater
    )

    def "CancelOfferBehaviour - should find offer and update offer status to be cancelled"() {
        setup:
        def merchantId = 1001
        def offerId = 1

        def offer = Mock(Offer)

        when:
        cancelOfferBehaviour.cancelOffer(merchantId, offerId)

        then:
        1 * offerDataFetcher.fetch(_, _) >> Optional.of(offer)
        1 * offer.setOfferStatus(OfferStatus.CANCELLED)
        1 * offerDataUpdater.update(merchantId, offerId, offer)
    }

    def "CancelOfferBehaviour - no updates should be performed when no offer found"() {
        setup:
        def merchantId = 1001
        def offerId = 1
        def offer = Mock(Offer)

        when:
        cancelOfferBehaviour.cancelOffer(merchantId, offerId)

        then:
        1 * offerDataFetcher.fetch(_, _) >> Optional.empty()
        0 * offer.setOfferStatus(_)
        0 * offerDataUpdater.update(_, _, _)
    }
}
