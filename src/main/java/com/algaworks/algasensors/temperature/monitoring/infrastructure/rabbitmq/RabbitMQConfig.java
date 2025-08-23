package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
    public static final String QUEUE_PROCESS_TEMPERATURE_DLQ = "temperature-monitoring.process-temperature.v1.dlq";
    public static final String QUEUE_ALERTING = "temperature-monitoring.alerting.v1.q";

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(final ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queueProcessTemperature(){
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_PROCESS_TEMPERATURE_DLQ)
                .build();
    }

    @Bean
    Queue deadLetterQueueProcessTemperature(){
        return QueueBuilder.durable(QUEUE_PROCESS_TEMPERATURE_DLQ).build();
    }

    @Bean
    Queue queueAlerting(){
        return QueueBuilder.durable(QUEUE_ALERTING).build();
    }

    private FanoutExchange exchange(){
        return ExchangeBuilder
                .fanoutExchange("temperature-processing.temperature-received.v1.e")
                .build();
    }

    @Bean
    Binding bindingProcessTemperature(){
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }

    @Bean
    Binding bindingAlerting(){
        return BindingBuilder.bind(queueAlerting()).to(exchange());
    }

}
