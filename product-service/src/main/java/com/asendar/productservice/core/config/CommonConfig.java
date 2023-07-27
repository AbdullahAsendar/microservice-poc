package com.asendar.productservice.core.config;

import com.asendar.productservice.command.interceptor.ProductCommandInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author asendar
 */
@Configuration
public class CommonConfig {

    @Autowired
    public void registerProductCommandInterceptor(ProductCommandInterceptor interceptor, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(interceptor);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("product-group", conf -> PropagatingErrorHandler.instance());
    }

    @Bean
    public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
    }

}
