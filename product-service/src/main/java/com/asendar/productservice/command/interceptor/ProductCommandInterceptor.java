package com.asendar.productservice.command.interceptor;

import com.asendar.productservice.command.CreateProductCommand;
import com.asendar.productservice.command.repository.ProductLookupRepository;
import com.asendar.productservice.core.exception.ProductServiceException;
import com.netflix.discovery.converters.Auto;
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
public class ProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    @Autowired
    private ProductLookupRepository productLookupRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            log.info("Intercepted command: {}", command.getPayloadType());
            if (command.getPayloadType().equals(CreateProductCommand.class)) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
                if (createProductCommand.price().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ProductServiceException("price cannot be less or equal than zero");
                }
                if (StringUtils.isBlank(createProductCommand.title())) {
                    throw new ProductServiceException("title cannot be empty");
                }
                if(productLookupRepository.existsByProductIdOrTitle(createProductCommand.productId(), createProductCommand.title())) {
                    throw new ProductServiceException("product already exists");
                }
            }
            return command;
        };
    }
}
