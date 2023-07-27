package com.asendar.orderservice.command.event;


import com.asendar.orderservice.core.entity.OrderEntity;
import com.asendar.orderservice.core.event.OrderApprovedEvent;
import com.asendar.orderservice.core.event.OrderCreatedEvent;
import com.asendar.orderservice.core.event.OrderRejectedEvent;
import com.asendar.orderservice.core.repository.OrderRepository;
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
public class OrderEventsHandler {

    @Autowired
    private OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) {
        OrderEntity orderEntity = orderRepository.findById(event.orderId()).orElse(null);
        orderEntity.setOrderStatus(event.orderStatus());
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {
        OrderEntity orderEntity = orderRepository.findById(event.orderId()).orElse(null);
        orderEntity.setOrderStatus(event.orderStatus());
        orderRepository.save(orderEntity);
    }


}
