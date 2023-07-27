package com.asendar.orderservice.command.repository;

import com.asendar.orderservice.command.entity.OrderLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author asendar
 */
@Repository
public interface OrderLookupRepository extends JpaRepository<OrderLookupEntity, String> {
    boolean existsByOrderId(String id);
}
