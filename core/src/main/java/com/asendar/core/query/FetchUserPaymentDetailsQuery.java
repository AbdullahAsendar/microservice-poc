package com.asendar.core.query;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record FetchUserPaymentDetailsQuery(String userId) {
}
