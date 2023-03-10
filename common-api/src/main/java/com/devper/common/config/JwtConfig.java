package com.devper.common.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public Algorithm algorithm(@Value("${jwt.algorithm.secret}") String jwtAlgorithmSecret){
        return Algorithm.HMAC512(jwtAlgorithmSecret);
    }
}
