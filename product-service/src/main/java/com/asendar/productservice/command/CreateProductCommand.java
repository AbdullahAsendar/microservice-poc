package com.asendar.productservice.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Builder
public record CreateProductCommand(@TargetAggregateIdentifier String productId, String title, BigDecimal price, Integer quantity) {
}
