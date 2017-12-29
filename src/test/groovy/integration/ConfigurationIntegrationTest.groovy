package integration

import com.worldpay.offersapi.domain.persistence.OfferDataPersister
import com.worldpay.offersapi.library.RoutingManager
import integration.helper.ClassPathXmlApplicationContextFactory
import spock.lang.Specification

class ConfigurationIntegrationTest extends Specification {

    def setup() {
        System.setProperty("spring.profiles.active", "test")
    }

    def "The DI configuration loads correctly"() {
        setup:
        def applicationContext = ClassPathXmlApplicationContextFactory.create()

        when:
        applicationContext.getBean(RoutingManager.class)
        applicationContext.getBean(OfferDataPersister.class)

        then:
        noExceptionThrown()
    }
}
