package com.asendar.orderservice.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author asendar
 */
@Builder
public record ApproveOrderCommand(@TargetAggregateIdentifier String orderId) {
}
