package com.devper.common.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.devper.common.exception.JwtVerificationException;
import com.devper.common.filter.JwtAuthenticationFilter;
import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BaseWebSecurityConfig {
    @Autowired
    private JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            String responseMsg = "Access denied!";
            if (authException.getCause() instanceof TokenExpiredException) {
                responseMsg = authException.getCause().getMessage();
            }
            if (authException instanceof JwtVerificationException) {
                responseMsg = "Invalid token!";
            }
            ResponseWrapper responseWrapper = ResponseWrapper.builder().errors(List.of(responseMsg)).hasErrors(true).content(null).status(403).timestamp(System.currentTimeMillis()).build();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
            response.getWriter().flush();
        };
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().permitAll();
        http.addFilterAfter(new JwtAuthenticationFilter(jwtService, authenticationEntryPoint()), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        return http.build();
    }
}
