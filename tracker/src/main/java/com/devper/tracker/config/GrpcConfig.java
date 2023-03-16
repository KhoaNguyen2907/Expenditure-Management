package com.devper.tracker.config;

import com.devper.tracker.grpc.ReportClientGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class GrpcConfig {
    @Value("${grpc.server.report.host}")
    private String host;

    @Value("${grpc.server.report.port}")
    private int port;

    @Bean
    public ReportClientGrpc reportClientGrpc() {
        return new ReportClientGrpc(host, port);
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        reportClientGrpc().shutdown();
    }
}
