package com.devper.common.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService{
    @Autowired
    private Algorithm jwtAlgorithm;

    @Override
    public String generateToken(UserDetails userDetails) {
        String[] roles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).toArray(String[]::new);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .withArrayClaim("roles", roles)
                .sign(jwtAlgorithm);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return JWT.require(jwtAlgorithm).build().verify(token).getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        return JWT.require(jwtAlgorithm).build().verify(token).getExpiresAt().after(new Date());
    }

    @Override
    public Set<GrantedAuthority> getRolesFromToken(String token) {
        return JWT.require(jwtAlgorithm).build().verify(token)
                .getClaim("roles").asList(String.class)
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
