package com.asendar.productservice.command.event;

import com.asendar.core.event.ProductReservationCancelled;
import com.asendar.core.event.ProductReservedEvent;
import com.asendar.productservice.core.entity.ProductEntity;
import com.asendar.productservice.core.event.ProductCreatedEvent;
import com.asendar.productservice.core.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Slf4j
@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity productEntity = productRepository.findById(event.productId()).orElse(null);
        productEntity.setQuantity(productEntity.getQuantity() - event.quantity());
        productRepository.save(productEntity);
        log.info("ProductReservedEvent is called for productId: " + event.productId());
    }

    @EventHandler
    public void on(ProductReservationCancelled event) {
        ProductEntity productEntity = productRepository.findById(event.productId()).orElse(null);
        productEntity.setQuantity(productEntity.getQuantity() + event.quantity());
        productRepository.save(productEntity);
        log.info("ProductReservationCancelled is called for productId: " + event.productId());
    }
}
