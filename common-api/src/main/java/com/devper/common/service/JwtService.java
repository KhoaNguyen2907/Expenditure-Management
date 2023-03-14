package com.devper.common.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface JwtService {

        String generateToken(UserDetails userDetails);

        String getUsernameFromToken(String token);

        boolean validateToken(String token);

        Set<GrantedAuthority> getRolesFromToken(String token);

        void setAuthentication(String token);
}
