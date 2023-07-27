package com.asendar.core.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author asendar
 */
@Builder
public record CancelProductReservationCommand(@TargetAggregateIdentifier String productId, int quantity, String orderId,
                                              String userId, String reason) {
}
