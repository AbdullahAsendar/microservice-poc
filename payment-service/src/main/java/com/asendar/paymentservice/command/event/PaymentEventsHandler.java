package com.asendar.paymentservice.command.event;

import com.asendar.core.event.PaymentProcessedEvent;
import com.asendar.paymentservice.core.entity.PaymentEntity;
import com.asendar.paymentservice.core.repository.PaymnetRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
public class PaymentEventsHandler {
    @Autowired
    private PaymnetRepository paymnetRepository;

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(event.paymentId());
        payment.setOrderId(event.orderId());
        paymnetRepository.save(payment);
    }
}
