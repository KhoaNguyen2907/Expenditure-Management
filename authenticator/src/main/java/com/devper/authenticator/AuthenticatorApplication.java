package com.devper.authenticator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.devper.authenticator", "com.devper.common"})
public class AuthenticatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticatorApplication.class, args);
    }
}
