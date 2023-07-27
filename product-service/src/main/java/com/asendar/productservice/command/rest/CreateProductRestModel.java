package com.asendar.productservice.command.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Builder
public record CreateProductRestModel(
        @NotBlank(message = "title is required") String title,
        @Min(value = 1, message = "price cannot be less than 1") BigDecimal price,
        @Min(value = 0, message = "quantity cannot be less than 0") @Max(value = 1000, message = "quantity cannot be greater than 1000") Integer quantity
) {
}
