package com.asendar.orderservice.command.rest;



import com.asendar.core.model.OrderStatus;
import com.asendar.orderservice.command.CreateOrderCommand;
import com.asendar.orderservice.core.aspect.ResponseEnvelope;
import com.asendar.orderservice.core.entity.OrderEntity;
import com.asendar.orderservice.query.FindOrderQuery;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author asendar
 */
@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody OrderRestModel product) {
        var command = CreateOrderCommand.builder()//
                .orderId(UUID.randomUUID().toString())//
                .productId(product.productId())//
                .userId(product.userId())//
                .quantity(product.quantity())//
                .addressId(product.addressId())//
                .orderStatus(OrderStatus.CREATED)//
                .build();

       try(var subscriptionResult = queryGateway.subscriptionQuery(
               new FindOrderQuery(command.orderId()),
               ResponseTypes.instanceOf(com.asendar.orderservice.query.rest.OrderRestModel.class),
               ResponseTypes.instanceOf(com.asendar.orderservice.query.rest.OrderRestModel.class))){
           commandGateway.sendAndWait(command);
           return ResponseEntity.ok(ResponseEnvelope.success(subscriptionResult.updates().blockFirst()));
        }


    }

}
