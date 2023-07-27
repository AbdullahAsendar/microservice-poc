package com.asendar.productservice.query.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Builder
public record ProductRestModel(String productId, String title, BigDecimal price, Integer quantity) {

}
