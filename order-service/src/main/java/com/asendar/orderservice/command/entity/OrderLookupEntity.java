package com.asendar.orderservice.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asendar
 */
@Entity
@Table(name = "ord_order_lookup")

@Setter
@Getter
@ToString
public class OrderLookupEntity {
    @Id
    private String orderId;

}
