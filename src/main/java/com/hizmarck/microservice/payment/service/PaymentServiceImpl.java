package com.hizmarck.microservice.payment.service;

import com.hizmarck.microservice.payment.domain.Payment;
import com.hizmarck.microservice.payment.domain.PaymentRequest;
import com.hizmarck.microservice.payment.exception.PaymentException;
import com.hizmarck.microservice.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaymentServiceImpl implements PaymentService{

    public final static MathContext ctx = MathContext.DECIMAL64;
    private final static BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private final static BigDecimal T_WEEK =  BigDecimal.ONE.divide(YEAR_IN_WEEKS, ctx);

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> all() {
        return repository.findAll();
    }

    @Override
    public List<Payment> calculatePaymentsByWeeks(PaymentRequest request) {
        // start all rules validations
        validateRequest(request);

        var today = LocalDate.now();
        var terms = new BigDecimal(request.terms().toString());
        // A = P(1 + rate * Time)
        var rate = div(request.rate() , ONE_HUNDRED).setScale(2, ctx.getRoundingMode());
        var time = div( terms , YEAR_IN_WEEKS);
        var accrued = mult( request.amount(), add( BigDecimal.ONE,  mult(rate, time) )).setScale(2, ctx.getRoundingMode());
        // row calculations
        var interestByWeek = mult(request.amount(), mult(rate, T_WEEK));
        var paymentByWeek = add( div(request.amount() , terms) , interestByWeek).setScale(2, ctx.getRoundingMode());

        // the (time) must be expressed in the time unit of the (rate)
        // by simplify we assume that the rate always is annual.
        return IntStream.range(1, request.terms() + 1)
                .mapToObj(i -> {
                    // for the last payment make an adjustment for the trails decimals
                    // e.g. pending = 0.1  then add this amount to the payment week and pending = 0
                    BigDecimal pending = sub(accrued, mult(new BigDecimal(i), paymentByWeek));
                    BigDecimal amount = i == request.terms() ? add(paymentByWeek, pending) : paymentByWeek;
                    return new Payment(i, amount, i == request.terms() ? BigDecimal.ZERO : pending, today.plusWeeks(i));
                }).toList();
    }

    @Override
    public List<Payment> calculatePaymentsByWeekAndSave(PaymentRequest request) {
        return repository.saveAll(this.calculatePaymentsByWeeks(request));
    }

    private void validateRequest(PaymentRequest request) {
        if ( !(request.terms() >= 4 && request.terms() <= 52) ) {
            throw new PaymentException(PaymentException.OUT_OF_WEEK_RANGE);
        }

        if ( !(request.rate().compareTo(BigDecimal.ONE) > 0 && request.rate().compareTo(ONE_HUNDRED) < 0 ) ) {
            throw new PaymentException(PaymentException.OUT_OF_RATE_RANGE);
        }

        if ( !(request.amount().compareTo(BigDecimal.ONE) > 0 && request.amount().compareTo(new BigDecimal("999999")) <0 )) {
            throw new PaymentException(PaymentException.OUT_OF_AMOUNT_RANGE);
        }
    }

    // some BigDecimal operations with defined context
    private BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b, ctx);
    }

    private BigDecimal mult(BigDecimal a, BigDecimal b) {
        return a.multiply(b, ctx);
    }

    private BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b, ctx);
    }

    private BigDecimal sub(BigDecimal a, BigDecimal b) {
        return a.subtract(b, ctx);
    }

}
