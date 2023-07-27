package com.asendar.orderservice.command.event;

import com.asendar.orderservice.command.entity.OrderLookupEntity;
import com.asendar.orderservice.command.repository.OrderLookupRepository;
import com.asendar.orderservice.core.event.OrderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
@ProcessingGroup("order-group")
public class OrderLookupEventHandler {

    @Autowired
    private OrderLookupRepository orderLookupRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderLookupEntity orderLookupEntity = new OrderLookupEntity();
        BeanUtils.copyProperties(event, orderLookupEntity);
        orderLookupRepository.save(orderLookupEntity);
    }

}
