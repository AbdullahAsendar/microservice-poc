package com.asendar.productservice.query.rest;

import com.asendar.productservice.query.FindProductsQuery;
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
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public ResponseEntity<List<ProductRestModel>> getProducts() {
        FindProductsQuery query = new FindProductsQuery();
        return ResponseEntity.ok(queryGateway.query(query, ProductListRestModel.class).join().products());
    }
}
