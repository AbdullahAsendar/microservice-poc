package com.asendar.productservice.query.rest;


import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author asendar
 */
@Builder
public record ProductListRestModel(List<ProductRestModel> products) {
}
