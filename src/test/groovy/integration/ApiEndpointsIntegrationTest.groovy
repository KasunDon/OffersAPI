package integration

import com.worldpay.offersapi.library.RoutingManager
import groovyx.net.http.HTTPBuilder
import integration.helper.ClassPathXmlApplicationContextFactory
import spark.Spark
import spock.lang.Specification

import java.time.Instant

import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST
import static groovyx.net.http.Method.PUT

class ApiEndpointsIntegrationTest extends Specification {

    def setup() {

        System.setProperty("spring.profiles.active", "test")

        def applicationContext = ClassPathXmlApplicationContextFactory.create()

        applicationContext
            .getBean(RoutingManager.class)
            .route()
    }

    def "Testing Offer endpoints"() {
        setup:
        def http = new HTTPBuilder("http://localhost:4576/")
        def expiryTime1 =
            Instant
                .now()
                .plusSeconds(2)
                .getEpochSecond()

        when:

        http.request(PUT) {
            uri.path = '/1001/create-offer'
            requestContentType = URLENC
            body = [description: 'offer1', currency: 'GBP', price: '1.11', expiryTime: expiryTime1]

            response.success = { resp ->
                assert resp.statusLine.statusCode == 200
            }
        }

        http.request(GET) {
            uri.path = '/1001/display-offer/1'
            response.success = { resp, content ->
                assert resp.statusLine.statusCode == 200

                assert content.offer.id == 1
                assert content.offer.merchant.id == 1001
                assert content.offer.description == "offer1"
                assert content.offer.price.currency == "GBP"
                assert content.offer.offerStatus == "ACTIVE"
                assert content.offer.price.amount == 1.11
                assert content.offer.expiryTime.seconds == expiryTime1
            }
        }

        sleep(2000)

        http.request(GET) {
            uri.path = '/1001/display-offer/1'
            response.success = { resp, content ->
                assert resp.statusLine.statusCode == 200

                assert content.offer.id == 1
                assert content.offer.merchant.id == 1001
                assert content.offer.description == "offer1"
                assert content.offer.price.currency == "GBP"
                assert content.offer.offerStatus == "EXPIRED"
                assert content.offer.price.amount == 1.11
                assert content.offer.expiryTime.seconds == expiryTime1
            }
        }


        def expiryTime2 =
            Instant
                .now()
                .plusSeconds(10)
                .getEpochSecond()

        http.request(PUT) {
            uri.path = '/1001/create-offer'
            requestContentType = URLENC
            body = [description: 'offer2', currency: 'GBP', price: '1.50', expiryTime: expiryTime2]

            response.success = { resp ->
                assert resp.statusLine.statusCode == 200
            }
        }

        http.request(GET) {
            uri.path = '/1001/display-offer/2'
            response.success = { resp, content ->
                assert resp.statusLine.statusCode == 200

                assert content.offer.id == 2
                assert content.offer.merchant.id == 1001
                assert content.offer.description == "offer2"
                assert content.offer.price.currency == "GBP"
                assert content.offer.offerStatus == "ACTIVE"
                assert content.offer.price.amount == 1.50
                assert content.offer.expiryTime.seconds == expiryTime2
            }
        }

        http.request(POST) {
            uri.path = '/1001/cancel-offer/2'

            response.success = { resp ->
                assert resp.statusLine.statusCode == 200
            }
        }

        http.request(GET) {
            uri.path = '/1001/display-offer/2'
            response.success = { resp, content ->
                assert resp.statusLine.statusCode == 200

                assert content.offer.id == 2
                assert content.offer.merchant.id == 1001
                assert content.offer.description == "offer2"
                assert content.offer.price.currency == "GBP"
                assert content.offer.offerStatus == "CANCELLED"
                assert content.offer.price.amount == 1.50
                assert content.offer.expiryTime.seconds == expiryTime2
            }
        }

        then:

        1 == 1
    }

    def cleanup() {
        Spark.stop()
    }

}
