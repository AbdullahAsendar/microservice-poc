package com.asendar.core.event;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record PaymentProcessedEvent(
        String paymentId,
        String orderId) {
}
