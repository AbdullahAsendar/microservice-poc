package com.asendar.orderservice.saga;

import com.asendar.core.command.CancelProductReservationCommand;
import com.asendar.core.command.ProcessPaymentCommand;
import com.asendar.core.command.ReserveProductCommand;
import com.asendar.core.event.PaymentProcessedEvent;
import com.asendar.core.event.ProductReservationCancelled;
import com.asendar.core.event.ProductReservedEvent;
import com.asendar.core.model.User;
import com.asendar.core.query.FetchUserPaymentDetailsQuery;
import com.asendar.orderservice.command.ApproveOrderCommand;
import com.asendar.orderservice.command.RejectOrderCommand;
import com.asendar.orderservice.core.event.OrderApprovedEvent;
import com.asendar.orderservice.core.event.OrderCreatedEvent;
import com.asendar.orderservice.core.event.OrderRejectedEvent;
import com.asendar.orderservice.core.exception.OrderServiceException;
import com.asendar.orderservice.query.FindOrderQuery;
import com.asendar.orderservice.query.rest.OrderRestModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * @author asendar
 */
@Saga
@Slf4j
public class OrderSaga {

    private static final String PAYMENT_PROCESSING_DEADLINE = "payment-processing-deadline";

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private transient DeadlineManager deadlineManager;

    @Autowired
    private transient QueryUpdateEmitter queryUpdateEmitter;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(event.orderId())
                .productId(event.productId())
                .quantity(event.quantity())
                .userId(event.userId())
                .build();
        log.info("OrderCreatedEvent handled for orderId: " + event.orderId());
        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                // compensate transaction
                log.error("exception", commandResultMessage.exceptionResult());
                RejectOrderCommand command = RejectOrderCommand.builder()
                        .orderId(event.orderId())
                        .reason(commandResultMessage.exceptionResult().getMessage())
                        .build();
                commandGateway.send(command);
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        // process payment
        log.info("ProductReservedEvent handled for orderId: " + event.orderId());
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(event.userId());

        try {
            User user = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
            if (user == null)
                throw new OrderServiceException("Could not fetch user payment details for userId: " + event.userId());

            deadlineManager.schedule(
                    Duration.of(10, ChronoUnit.SECONDS),
                    PAYMENT_PROCESSING_DEADLINE,
                    event);

            ProcessPaymentCommand command = ProcessPaymentCommand.builder()
                    .orderId(event.orderId())
                    .paymentId(UUID.randomUUID().toString())
                    .paymentDetails(user.paymentDetails())
                    .build();

            String processPaymentResult = commandGateway.sendAndWait(command);
            if (processPaymentResult == null)
                throw new OrderServiceException("Could not process payment for orderId: " + event.orderId());

        } catch (Exception e) {
            // compensate transaction
            log.error("exception", e);
            cancelProductReservation(event, e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelled event) {
        log.info("ProductReservationCancelled handled for orderId: " + event.orderId());
        RejectOrderCommand command = RejectOrderCommand.builder()
                .orderId(event.orderId())
                .reason(event.reason())
                .build();
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        deadlineManager.cancelAll(PAYMENT_PROCESSING_DEADLINE);

        log.info("PaymentProcessedEvent handled for orderId: " + event.orderId());
        ApproveOrderCommand command = ApproveOrderCommand.builder()
                .orderId(event.orderId())
                .build();
        commandGateway.send(command);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent event) {
        log.info("OrderApprovedEvent handled for orderId: " + event.orderId());
        queryUpdateEmitter.emit(FindOrderQuery.class,
                query -> true,
                OrderRestModel.builder()
                        .orderId(event.orderId())
                        .orderStatus(event.orderStatus())
                        .build());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent event) {
        log.info("OrderRejectedEvent handled for orderId: " + event.orderId());
        queryUpdateEmitter.emit(FindOrderQuery.class,
                query -> true,
                OrderRestModel.builder()
                        .orderId(event.orderId())
                        .orderStatus(event.orderStatus())
                        .build());
    }

    @DeadlineHandler(deadlineName = PAYMENT_PROCESSING_DEADLINE)
    public void handleDeadline(ProductReservedEvent event) {
        log.info("Payment processing deadline took place for orderId: " + event.orderId());
        cancelProductReservation(event, "Payment timeout");
    }

    /*******************************************************************************************************************
     * HELPERS
     */
    private void cancelProductReservation(ProductReservedEvent event, String reason) {
        deadlineManager.cancelAll(PAYMENT_PROCESSING_DEADLINE);
        CancelProductReservationCommand command = CancelProductReservationCommand.builder()
                .orderId(event.orderId())
                .productId(event.productId())
                .quantity(event.quantity())
                .userId(event.userId())
                .reason(reason)
                .build();

        commandGateway.send(command);
    }
}
