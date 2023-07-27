package com.asendar.core.event;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record ProductReservationCancelled(String productId, int quantity, String orderId, String userId, String reason) {
}
