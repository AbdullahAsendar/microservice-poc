package com.asendar.core.event;

import com.asendar.core.model.OrderStatus;
import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record OrderCreatedEvent(String orderId, String userId, String productId, Integer quantity, String addressId, OrderStatus orderStatus) {

}
