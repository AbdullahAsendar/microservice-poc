package com.asendar.orderservice.core.config;

import com.asendar.orderservice.command.interceptor.OrderCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author asendar
 */
@Configuration
public class CommonConfig {

    @Autowired
    public void registerOrderCommandInterceptor(OrderCommandInterceptor interceptor, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(interceptor);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("order-group", conf -> PropagatingErrorHandler.instance());
    }

    @Bean
    public DeadlineManager deadlineManager(org.axonframework.config.Configuration config, SpringTransactionManager transactionManager) {
        return SimpleDeadlineManager.builder()
                .scopeAwareProvider(new ConfigurationScopeAwareProvider(config))
                .transactionManager(transactionManager)
                .build();
    }

}
