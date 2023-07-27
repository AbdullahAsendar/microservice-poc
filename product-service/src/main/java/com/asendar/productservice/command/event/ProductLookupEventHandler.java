package com.asendar.productservice.command.event;

import com.asendar.productservice.command.entity.ProductLookupEntity;
import com.asendar.productservice.command.repository.ProductLookupRepository;
import com.asendar.productservice.core.entity.ProductEntity;
import com.asendar.productservice.core.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity();
        BeanUtils.copyProperties(event, productLookupEntity);
        productLookupRepository.save(productLookupEntity);
    }

}
