package com.devper.reporter.rabbitmq;

import com.devper.common.exception.JwtVerificationException;
import com.devper.common.service.JwtService;
import com.devper.reporter.model.request.TransactionRequest;
import com.devper.reporter.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReportConsumer {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ReportService reportService;
    @Autowired
    private JwtService jwtService;


//    @RabbitListener(queues = "${rabbitmq.queue.reporter}")
//    public void consume(TransactionRequest transaction) {
//        log.info("Received message from queue: {}", transaction);
//        reportService.processNewTransaction(transaction);
//        log.info("Message processed");
//    }

    @RabbitListener(
            queuesToDeclare = @Queue(
                    value = "${rabbitmq.queue.reporter}",
                    durable = "true",
                    autoDelete = "false"
            )
    )
    public void consume(Message message) throws JsonProcessingException {
        String messageBody = new String(message.getBody());
        System.out.println("Received message from queue: " + messageBody);
        TransactionRequest transaction = objectMapper.readValue(messageBody, TransactionRequest.class);
        String token = message.getMessageProperties().getHeader("Authorization");
        log.info("token: {}", token);
        if (token == null) {
            throw new JwtVerificationException("Token is null. Access denied");
        }
        token = token.replace("Bearer ", "");

        if (jwtService.validateToken(token)) {
            jwtService.setAuthentication(token);
        } else {
            throw new JwtVerificationException("Token is invalid. Access denied");
        }

        reportService.processNewTransaction(transaction);
        log.info("Message processed");

    }

}
