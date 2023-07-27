package com.asendar.orderservice.command.interceptor;


import com.asendar.orderservice.command.CreateOrderCommand;
import com.asendar.orderservice.command.repository.OrderLookupRepository;
import com.asendar.orderservice.core.exception.OrderServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author asendar
 */
@Slf4j
@Component
public class OrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Autowired
    private OrderLookupRepository orderLookupRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            log.info("Intercepted command: {}", command.getPayloadType());
            if (command.getPayloadType().equals(CreateOrderCommand.class)) {
                CreateOrderCommand createOrderCommand = (CreateOrderCommand) command.getPayload();
                if (createOrderCommand.userId() == null) {
                    throw new OrderServiceException("userId cannot be empty");
                }
                if (createOrderCommand.productId() == null) {
                    throw new OrderServiceException("productId cannot be empty");
                }
                if (orderLookupRepository.existsByOrderId(createOrderCommand.orderId())) {
                    throw new OrderServiceException("order already exists");
                }
            }
            return command;
        };
    }
}
