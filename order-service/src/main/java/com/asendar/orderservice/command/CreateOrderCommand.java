package com.asendar.orderservice.command;

import com.asendar.core.model.OrderStatus;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author asendar
 */
@Builder
public record CreateOrderCommand(
        @TargetAggregateIdentifier String orderId,
        String userId,
        String productId,
        int quantity,
        String addressId,
        OrderStatus orderStatus) {
}
