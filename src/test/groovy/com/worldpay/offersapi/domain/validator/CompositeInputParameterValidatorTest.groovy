package com.worldpay.offersapi.domain.validator

import com.worldpay.offersapi.domain.entity.parameters.RequestParameter
import spock.lang.Specification

class CompositeInputParameterValidatorTest extends Specification {

    def "CompositeInputParameterValidator - should iterate through given validators"() {
        setup:
        def validator1 = Mock(InputParameterValidator)
        def validator2 = Mock(InputParameterValidator)

        def compositeInputParameterValidator = new CompositeInputParameterValidator([
            validator1,
            validator2
        ])

        def requestParameter = Mock(RequestParameter)

        when:
        compositeInputParameterValidator.validate(requestParameter)

        then:
        1 * validator1.validate(_) >> true
        1 * validator2.validate(_) >> true
    }

    def "CompositeInputParameterValidator - should halt iterations when exception is thrown out of validators"() {
        setup:
        def validator1 = Mock(InputParameterValidator)
        def validator2 = Mock(InputParameterValidator)

        def compositeInputParameterValidator = new CompositeInputParameterValidator([
            validator1,
            validator2
        ])

        def requestParameter = Mock(RequestParameter)

        when:
        compositeInputParameterValidator.validate(requestParameter)

        then:
        1 * validator1.validate(_) >> {
            throw new IllegalStateException()
        }

        thrown(IllegalStateException)

        0 * validator2.validate(_)
    }
}
