package com.devper.gateway.config;

import com.devper.gateway.JwtHeaderGatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public JwtHeaderGatewayFilter jwtHeaderGatewayFilter() {
        return new JwtHeaderGatewayFilter();
    }
}
