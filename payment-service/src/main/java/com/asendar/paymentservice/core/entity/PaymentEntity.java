package com.asendar.paymentservice.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Entity
@Table(name = "ord_payment")

@Setter
@Getter
@ToString
public class PaymentEntity {
    @Id
    private String paymentId;
    private String orderId;
}