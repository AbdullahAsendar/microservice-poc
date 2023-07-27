package com.asendar.productservice.query;

import com.asendar.productservice.core.repository.ProductRepository;
import com.asendar.productservice.query.rest.ProductListRestModel;
import com.asendar.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
public class ProductsQueryHandler {
    @Autowired
    private ProductRepository productRepository;

    @QueryHandler
    public ProductListRestModel findProducts(FindProductsQuery query) {
        return new ProductListRestModel(productRepository.findAll().stream().map(entity ->
                ProductRestModel.builder()
                        .productId(entity.getProductId())
                        .title(entity.getTitle())
                        .price(entity.getPrice())
                        .quantity(entity.getQuantity())
                        .build())
                .toList());
    }
}
