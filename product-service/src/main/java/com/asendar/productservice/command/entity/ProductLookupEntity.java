package com.asendar.productservice.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asendar
 */
@Entity
@Table(name = "prd_product_lookup")

@Setter
@Getter
@ToString
public class ProductLookupEntity {
    @Id
    private String productId;
    @Column(unique = true)
    private String title;
}
