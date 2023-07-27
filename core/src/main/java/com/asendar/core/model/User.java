package com.asendar.core.model;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record User(String firstName, String lastName, String userId, PaymentDetails paymentDetails) {
}
