package com.hizmarck.microservice.payment;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EntityScan("com.hizmarck.microservice.payment")
@EnableJpaRepositories("com.hizmarck.microservice.payment")
public class PaymentConfiguration {

}
