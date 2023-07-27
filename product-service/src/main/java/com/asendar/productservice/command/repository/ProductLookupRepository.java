package com.asendar.productservice.command.repository;

import com.asendar.productservice.command.entity.ProductLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author asendar
 */
@Repository
public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String> {
    ProductLookupEntity findByProductIdOrTitle(String productId, String title);
    boolean existsByProductIdOrTitle(String productId, String title);
}
