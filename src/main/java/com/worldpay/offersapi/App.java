package com.worldpay.offersapi;

import com.worldpay.offersapi.infrastructure.spring.ClassPathXmlApplicationContextFactory;
import com.worldpay.offersapi.library.RoutingManager;
import org.springframework.context.ApplicationContext;

public class App {

    public static void main(
        String[] args
    ) {
        ApplicationContext applicationContext = ClassPathXmlApplicationContextFactory.getOrCreate();
        RoutingManager routingManager = applicationContext.getBean(RoutingManager.class);

        routingManager.route();
    }
}