package com.worldpay.offersapi.domain.behaviour

import com.worldpay.offersapi.domain.entity.Offer
import com.worldpay.offersapi.domain.persistence.OfferDataFetcher
import spock.lang.Specification

class DisplayOfferBehaviourTest extends Specification {

    def offerDataFetcher = Mock(OfferDataFetcher)

    def displayOfferBehaviour = new DisplayOfferBehaviour(
        offerDataFetcher
    )

    def "DisplayOfferBehaviour - returns fetched offer using merchantId and offerId"() {
        setup:
        def merchantId = 1001
        def offerId = 1

        def offer = Mock(Offer) {
            getId() >> offerId
        }

        when:
        def optionalOffer = displayOfferBehaviour.displayOffer(merchantId, offerId)

        then:
        1 * offerDataFetcher.fetch(_, _) >> Optional.of(offer)

        optionalOffer.get().id == offerId
    }

    def "DisplayOfferBehaviour - returns empty optional when no offer found"() {
        setup:
        def merchantId = 1001
        def offerId = 1

        when:
        def optionalOffer = displayOfferBehaviour.displayOffer(merchantId, offerId)

        then:
        1 * offerDataFetcher.fetch(_, _) >> Optional.empty()

        optionalOffer.isPresent() == false
    }
}
