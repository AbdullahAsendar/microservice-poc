package com.asendar.productservice.core.repository;

import com.asendar.productservice.core.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author asendar
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
}
