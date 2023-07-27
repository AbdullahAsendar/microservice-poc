package com.asendar.productservice.core.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Builder
public record ProductCreatedEvent(String productId, String title, BigDecimal price, Integer quantity) {

}
