package com.asendar.orderservice.core.entity;

import com.asendar.core.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author asendar
 */
@Entity
@Table(name = "ord_order")

@Setter
@Getter
@ToString
public class OrderEntity {
    @Id
    private String orderId;
    private String userId;
    private String productId;
    private Integer quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
