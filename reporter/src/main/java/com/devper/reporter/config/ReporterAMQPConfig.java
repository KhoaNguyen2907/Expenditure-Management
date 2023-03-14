package com.devper.reporter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class ReporterAMQPConfig {
    @Value("${rabbitmq.exchange.internal}")
    public  String internalExchange;

    @Value("${rabbitmq.queue.reporter}")
    public  String reporterQueue;

    @Value("${rabbitmq.routing-key.reporter}")
    public  String reporterRoutingKey;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

}
