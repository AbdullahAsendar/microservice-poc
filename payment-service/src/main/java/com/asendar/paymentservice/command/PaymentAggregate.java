package com.asendar.paymentservice.command;

import com.asendar.core.command.ProcessPaymentCommand;
import com.asendar.core.event.OrderCreatedEvent;
import com.asendar.core.event.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

/**
 * @author asendar
 */
@Aggregate
@Setter
@NoArgsConstructor
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand command) {
        PaymentProcessedEvent event = PaymentProcessedEvent.builder()
                .paymentId(command.paymentId())
                .orderId(command.orderId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        BeanUtils.copyProperties(event, this);
    }
}
