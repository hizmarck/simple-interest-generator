package com.hizmarck.microservice.payment.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Payment {

    @Id @GeneratedValue
    private Long id;
    @NonNull
    private Integer number;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private BigDecimal pending;
    @NonNull
    private LocalDate date;

}