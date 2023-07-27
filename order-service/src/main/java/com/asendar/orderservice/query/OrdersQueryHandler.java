package com.asendar.orderservice.query;

import com.asendar.orderservice.core.repository.OrderRepository;
import com.asendar.orderservice.query.rest.OrderListRestModel;
import com.asendar.orderservice.query.rest.OrderRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author asendar
 */
@Component
public class OrdersQueryHandler {
    @Autowired
    private OrderRepository orderRepository;

    @QueryHandler
    public OrderListRestModel findOrders(FindOrdersQuery query) {
        return new OrderListRestModel(orderRepository.findAll().stream().map(entity ->
                OrderRestModel.builder()
                        .orderId(entity.getOrderId())
                        .userId(entity.getUserId())
                        .productId(entity.getProductId())
                        .quantity(entity.getQuantity())
                        .addressId(entity.getAddressId())
                        .orderStatus(entity.getOrderStatus())
                        .build()
        ).toList());
    }

    @QueryHandler
    public OrderRestModel findOrder(FindOrderQuery query) {
        return orderRepository.findById(query.orderId()).map(entity ->
                OrderRestModel.builder()
                        .orderId(entity.getOrderId())
                        .userId(entity.getUserId())
                        .productId(entity.getProductId())
                        .quantity(entity.getQuantity())
                        .addressId(entity.getAddressId())
                        .orderStatus(entity.getOrderStatus())
                        .build()
        ).orElse(null);
    }
}
