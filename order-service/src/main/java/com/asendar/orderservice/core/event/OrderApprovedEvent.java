package com.asendar.orderservice.core.event;

import com.asendar.core.model.OrderStatus;
import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record OrderApprovedEvent (String orderId, OrderStatus orderStatus){
}
