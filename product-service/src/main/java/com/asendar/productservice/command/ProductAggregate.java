package com.asendar.productservice.command;

import com.asendar.core.command.CancelProductReservationCommand;
import com.asendar.core.command.ReserveProductCommand;
import com.asendar.core.event.ProductReservationCancelled;
import com.asendar.core.event.ProductReservedEvent;
import com.asendar.productservice.core.event.ProductCreatedEvent;
import com.asendar.productservice.core.exception.ProductServiceException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author asendar
 */
@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
@Setter
@NoArgsConstructor
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(command.productId())
                .title(command.title())
                .price(command.price())
                .quantity(command.quantity())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(ReserveProductCommand command) {
        if (this.quantity < command.quantity()) {
            throw new ProductServiceException("Insufficient number of items in stock");
        }
        ProductReservedEvent event = ProductReservedEvent.builder()
                .productId(command.productId())
                .quantity(command.quantity())
                .orderId(command.orderId())
                .userId(command.userId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelProductReservationCommand command){
        ProductReservationCancelled event = ProductReservationCancelled.builder()
                .productId(command.productId())
                .quantity(command.quantity())
                .orderId(command.orderId())
                .userId(command.userId())
                .reason(command.reason())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent event) {
        this.quantity = this.quantity - event.quantity();
    }

    @EventSourcingHandler
    public void on(ProductReservationCancelled event) {
        this.quantity = this.quantity + event.quantity();
    }


}
