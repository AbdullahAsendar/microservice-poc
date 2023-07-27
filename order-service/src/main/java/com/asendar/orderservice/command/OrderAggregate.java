package com.asendar.orderservice.command;

import com.asendar.core.model.OrderStatus;
import com.asendar.orderservice.core.entity.OrderEntity;
import com.asendar.orderservice.core.event.OrderApprovedEvent;
import com.asendar.orderservice.core.event.OrderCreatedEvent;
import com.asendar.orderservice.core.event.OrderRejectedEvent;
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
public class OrderAggregate {

    @AggregateIdentifier
    public String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(command.orderId())
                .userId(command.userId())
                .productId(command.productId())
                .quantity(command.quantity())
                .addressId(command.addressId())
                .orderStatus(command.orderStatus())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(ApproveOrderCommand command){
        OrderApprovedEvent event = OrderApprovedEvent.builder()
                .orderId(command.orderId())
                .orderStatus(OrderStatus.APPROVED)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RejectOrderCommand command){
        OrderRejectedEvent event = OrderRejectedEvent.builder()
                .orderId(command.orderId())
                .orderStatus(OrderStatus.REJECTED)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event) {
        this.orderStatus = event.orderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event) {
        this.orderStatus = event.orderStatus();
    }

}
