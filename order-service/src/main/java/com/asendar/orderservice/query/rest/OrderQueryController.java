package com.asendar.orderservice.query.rest;

import com.asendar.orderservice.query.FindOrdersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author asendar
 */
@RestController
@RequestMapping("/orders")
public class OrderQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public ResponseEntity<List<OrderRestModel>> getOrders() {
        FindOrdersQuery query = new FindOrdersQuery();
        return ResponseEntity.ok(queryGateway.query(query, OrderListRestModel.class).join().orders());
    }
}
