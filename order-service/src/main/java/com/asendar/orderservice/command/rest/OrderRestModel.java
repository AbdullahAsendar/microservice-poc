package com.asendar.orderservice.command.rest;

import com.asendar.core.model.OrderStatus;


/**
 * @author asendar
 */
public record OrderRestModel(String userId, String productId, int quantity, String addressId, OrderStatus orderStatus) {
}
