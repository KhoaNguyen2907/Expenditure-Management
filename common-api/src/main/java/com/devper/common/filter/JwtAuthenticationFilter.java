package com.devper.common.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devper.common.exception.JwtVerificationException;
import com.devper.common.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;

    private AuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(JwtService jwtService, AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtService = jwtService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get jwt token
        String token = request.getHeader("Authorization");
        log.info("token: {}", token);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.replace("Bearer ", "");
        boolean isValidToken = false;
        try {
            isValidToken = jwtService.validateToken(token);
        } catch (JWTVerificationException e) {
            authenticationEntryPoint.commence(request, response, new JwtVerificationException(e.getMessage(),e));
        }

        if (isValidToken){
            jwtService.setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
