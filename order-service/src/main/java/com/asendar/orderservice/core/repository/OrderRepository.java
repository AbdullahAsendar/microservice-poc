package com.asendar.orderservice.core.repository;

import com.asendar.orderservice.core.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author asendar
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
