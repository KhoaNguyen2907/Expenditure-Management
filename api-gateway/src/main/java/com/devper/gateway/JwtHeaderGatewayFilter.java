package com.devper.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtHeaderGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
         if (token != null) {
              exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, token);
         }
        return chain.filter(exchange);
    }
}
