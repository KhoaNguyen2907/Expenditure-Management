package com.devper.tracker.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@Data
public class TrackerAMQPConfig {
    @Value("${rabbitmq.exchange.internal}")
    private  String internalExchange;

    @Value("${rabbitmq.queue.reporter}")
    private  String reporterQueue;

    @Value("${rabbitmq.routing-key.reporter}")
    private String reporterRoutingKey;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


}
