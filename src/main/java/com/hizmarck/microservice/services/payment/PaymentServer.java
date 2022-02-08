package com.hizmarck.microservice.services.payment;

import com.hizmarck.microservice.payment.PaymentConfiguration;
import com.hizmarck.microservice.services.registration.RegistrationServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@Import(PaymentConfiguration.class)
public class PaymentServer {

    public static void main(String[] args) {
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // set configuration file
        System.setProperty("spring.config.name", "payment-server");

        SpringApplication.run(PaymentServer.class, args);
    }
}
