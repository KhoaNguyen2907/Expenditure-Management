package com.devper.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.devper.common", "com.devper.reporter"})
public class ReporterApplication {
    public static void main(String[] args) {
          SpringApplication.run(ReporterApplication.class, args);
    }
}
