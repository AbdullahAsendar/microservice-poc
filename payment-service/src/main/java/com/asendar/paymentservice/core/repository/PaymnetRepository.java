package com.asendar.paymentservice.core.repository;

import com.asendar.paymentservice.core.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author asendar
 */
@Repository
public interface PaymnetRepository extends JpaRepository<PaymentEntity, String> {
}
