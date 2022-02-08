package com.hizmarck.microservice.payment.rest;

import com.hizmarck.microservice.payment.domain.Payment;
import com.hizmarck.microservice.payment.domain.PaymentRequest;
import com.hizmarck.microservice.payment.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200") // for dev purposes
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @RequestMapping("/payment")
    public String index() {
        return "Payment Service Microservice";
    }

    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Payment> all() {
        return service.all();
    }

    @PostMapping(value = "/payments/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Payment> calculatePayments(@RequestBody PaymentRequest request) {
        return service.calculatePaymentsByWeekAndSave(request);
    }
}
