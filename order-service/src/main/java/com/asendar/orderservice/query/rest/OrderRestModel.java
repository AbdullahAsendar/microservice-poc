package com.asendar.orderservice.query.rest;

import com.asendar.core.model.OrderStatus;
import com.asendar.orderservice.core.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Builder
public record OrderRestModel(String orderId, String userId, String productId, Integer quantity, String addressId,
                             OrderStatus orderStatus) {

}
