package com.asendar.productservice.command.rest;


import com.asendar.productservice.command.CreateProductCommand;
import com.asendar.productservice.core.aspect.ResponseEnvelope;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author asendar
 */
@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductRestModel product) {

        var command = CreateProductCommand.builder()//
                .productId(UUID.randomUUID().toString())//
                .title(product.title())//
                .price(product.price())//
                .quantity(product.quantity())//
                .build();

        return ResponseEntity.ok(ResponseEnvelope.success(commandGateway.sendAndWait(command)));
    }

}
