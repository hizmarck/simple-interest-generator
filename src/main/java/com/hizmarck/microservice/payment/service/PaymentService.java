package com.hizmarck.microservice.payment.service;

import com.hizmarck.microservice.payment.domain.Payment;
import com.hizmarck.microservice.payment.domain.PaymentRequest;
import com.hizmarck.microservice.payment.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Payment Service
 * All logic for calculate the payments and its interest.
 */
public interface PaymentService {

    BigDecimal YEAR_IN_WEEKS = new BigDecimal("52");

    /**
     * Generate the list of payment by the request object.
     *
     * The range of weeks that must be generated is [4-52].
     * The range of rate will be (1%- 100%)
     * The range of amount will be ($1 - $999,999.00).
     * Every payment is expressed by week (yyyy-MM-dd).
     *
     * The simple interest formula:
     *  A = P(1 + rt)
     *
     * A = Accrued
     * P = Principal
     * r = Rate (annual)
     * t = time
     *
     * @param request information to calculate the list of payments
     * @return the list of payment generates
     */
    List<Payment> calculatePaymentsByWeeks(PaymentRequest request);


    /**
     * Use the algorithm from {@link #calculatePaymentsByWeeks(PaymentRequest)} to generate the list of payments and
     * save all in the system.
     * @param request information to calculate the list of payments
     * @return list of payments saved
     */
    List<Payment> calculatePaymentsByWeekAndSave(PaymentRequest request);

    /**
     * Return all the payments on the system.
     * @return list of payment saved.
     */
    List<Payment> all();
}
