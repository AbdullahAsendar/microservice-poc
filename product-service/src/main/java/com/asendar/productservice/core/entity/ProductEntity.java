package com.asendar.productservice.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Entity
@Table(name = "prd_product")

@Setter
@Getter
@ToString
public class ProductEntity {
    @Id
    private String productId;
    @Column(unique = true)
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
