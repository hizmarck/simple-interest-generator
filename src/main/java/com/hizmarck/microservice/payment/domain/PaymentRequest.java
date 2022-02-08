package com.hizmarck.microservice.payment.domain;

import java.math.BigDecimal;

public record PaymentRequest(BigDecimal amount, Integer terms, BigDecimal rate){}
