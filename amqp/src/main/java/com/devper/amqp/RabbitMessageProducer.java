package com.devper.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMessageProducer {
    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void send(String exchange, String routingKey, Object payload)  {
        String token = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            token = authentication.getCredentials().toString();
            log.info("Authentication: {}", authentication);
        } else {
            log.info("Authentication is null");
        }

        MessageProperties propertiesBuilder = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("Authorization", "Bearer " + token).build();
        Message message = null;
        try {
            message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(payload))
                    .andProperties(propertiesBuilder)
                    .build();
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
        }

        log.info("Send message to exchange: {}, routingKey: {}, message: {}", exchange, routingKey, message);
        amqpTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Send message to exchange: {}, routingKey: {}, message: {} success", exchange, routingKey, message);

    }
}
