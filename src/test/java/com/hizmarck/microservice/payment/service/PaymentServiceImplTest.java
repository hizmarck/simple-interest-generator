package com.hizmarck.microservice.payment.service;

import com.hizmarck.microservice.payment.domain.Payment;
import com.hizmarck.microservice.payment.domain.PaymentRequest;
import com.hizmarck.microservice.payment.exception.PaymentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PaymentServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImplTest.class);

    private final PaymentRequest OUT_AMOUNT_REQUEST_1 = new PaymentRequest(BigDecimal.ONE, 10, new BigDecimal("50.0"));
    private final PaymentRequest OUT_AMOUNT_REQUEST_2 = new PaymentRequest(new BigDecimal("999999.0"), 10, new BigDecimal("50.0"));

    private final PaymentRequest OUT_WEEK_REQUEST_1 = new PaymentRequest(new BigDecimal("1000.0"), 3, new BigDecimal("50.0"));
    private final PaymentRequest OUT_WEEK_REQUEST_2 = new PaymentRequest(new BigDecimal("1000.0"), 55, new BigDecimal("50.0"));

    private final PaymentRequest OUT_RATE_REQUEST_1 = new PaymentRequest(new BigDecimal("1000.0"), 20, BigDecimal.ONE);
    private final PaymentRequest OUT_RATE_REQUEST_2 = new PaymentRequest(new BigDecimal("1000.0"), 20, new BigDecimal("100.0"));

    // correct payment
    private final PaymentRequest FOUR_PAYMENTS_AND_TEN_RATE = new PaymentRequest(new BigDecimal("1000.0"), 4, BigDecimal.TEN);
    private final BigDecimal EXPECTED_ACCRUED = new BigDecimal("1007.69");

    @InjectMocks
    private PaymentServiceImpl service;

    @Test
    void calculatePaymentsThrowAmountException() {
        PaymentException exception1 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_AMOUNT_REQUEST_1));
        PaymentException exception2 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_AMOUNT_REQUEST_2));

        assertEquals(exception1.getMessage(), PaymentException.OUT_OF_AMOUNT_RANGE);
        assertEquals(exception2.getMessage(), PaymentException.OUT_OF_AMOUNT_RANGE);
    }

    @Test
    void calculatePaymentsThrowWeekException() {
        PaymentException exception1 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_WEEK_REQUEST_1));
        PaymentException exception2 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_WEEK_REQUEST_2));

        assertEquals(exception1.getMessage(), PaymentException.OUT_OF_WEEK_RANGE);
        assertEquals(exception2.getMessage(), PaymentException.OUT_OF_WEEK_RANGE);
    }

    @Test
    void calculatePaymentsThrowRateException() {
        PaymentException exception1 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_RATE_REQUEST_1));
        PaymentException exception2 = assertThrows(PaymentException.class, () -> service.calculatePaymentsByWeeks(OUT_RATE_REQUEST_2));

        assertEquals(exception1.getMessage(), PaymentException.OUT_OF_RATE_RANGE);
        assertEquals(exception2.getMessage(), PaymentException.OUT_OF_RATE_RANGE);
    }

    @Test
    void calculatePaymentsFourPaymentsAndTenRate() {
        List<Payment> payments = service.calculatePaymentsByWeeks(FOUR_PAYMENTS_AND_TEN_RATE);
        assertNotNull(payments, "The calculation of payments can't be null");

        log.info("The current value of the Request input " + FOUR_PAYMENTS_AND_TEN_RATE);
        assertEquals(4, payments.size(), "The total of payments must be 4");

        var today = LocalDate.now();
        var expectedStartWeekPayment = today.plusWeeks(1);
        var expectedEndWeekPayment = today.plusWeeks(4);
        var firstPayment = payments.get(0);
        var lastPayment = payments.get(3);

        // dates
        var firstPaymentDate = firstPayment.getDate();
        assertEquals(firstPaymentDate, expectedStartWeekPayment, String.format("The date of the first payment must be '%s' but found '%s'", expectedStartWeekPayment, firstPaymentDate));

        var endPaymentDate = lastPayment.getDate();
        assertEquals(firstPaymentDate, expectedStartWeekPayment, String.format("The date of the last payment must be '%s' but found '%s'", expectedEndWeekPayment, endPaymentDate));

        // pending amount, we only validate the last amount
        assertEquals(0, BigDecimal.ZERO.compareTo(lastPayment.getPending()), "The pending amount for the last payment must be 0");

        // make the summation of all quantities
        assertEquals(0, EXPECTED_ACCRUED.compareTo(payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, (x,y) -> x.add(y, PaymentServiceImpl.ctx) ) ));
    }
}