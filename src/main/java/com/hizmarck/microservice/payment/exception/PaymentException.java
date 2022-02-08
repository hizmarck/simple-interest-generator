package com.hizmarck.microservice.payment.exception;

public class PaymentException extends RuntimeException {

    public static String OUT_OF_WEEK_RANGE = "error.out.week.range";
    public static String OUT_OF_AMOUNT_RANGE = "error.out.amount.range";
    public static String OUT_OF_RATE_RANGE = "error.out.rate.range";

    public PaymentException(String code) {
        super(code);
    }
}