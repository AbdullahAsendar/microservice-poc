package com.asendar.orderservice.query;

import lombok.Builder;

/**
 * @author asendar
 */
@Builder
public record FindOrderQuery(String orderId) {
}
