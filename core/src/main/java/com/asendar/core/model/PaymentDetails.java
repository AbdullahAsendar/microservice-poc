package com.asendar.core.model;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record PaymentDetails(String name, String cardNumber, int validUntilMonth, int validUntilYear, String cvv) {
}
