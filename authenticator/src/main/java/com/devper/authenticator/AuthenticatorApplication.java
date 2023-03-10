package com.devper.authenticator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.devper.authenticator", "com.devper.common"})
public class AuthenticatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticatorApplication.class, args);
    }
}
