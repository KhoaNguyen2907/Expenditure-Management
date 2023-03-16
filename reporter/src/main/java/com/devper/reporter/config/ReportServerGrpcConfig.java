package com.devper.reporter.config;

import com.devper.reporter.grpc.ReportServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class ReportServerGrpcConfig {

    private Server server;

    @Value("${grpc.server.port}")
    private int port;

    @Autowired
    private ReportServiceGrpcImpl reportServiceGrpc;

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        server = ServerBuilder.forPort(port)
                .addService(reportServiceGrpc)
                .build()
                .start();
        System.out.println("Report gRPC server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            stop();
            System.err.println("Server shut down");
        }));
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

}
