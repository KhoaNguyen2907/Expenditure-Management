package com.devper.authenticator.service;

import com.devper.authenticator.dao.AppUserDAO;
import com.devper.authenticator.model.AppUser;
import com.devper.authenticator.model.request.LoginRequest;
import com.devper.authenticator.model.request.SignUpRequest;
import com.devper.authenticator.model.response.LoginResponse;
import com.devper.authenticator.model.response.SignUpResponse;
import com.devper.common.service.JwtService;
import com.devper.common.utils.ProjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticatorServiceImpl implements AuthenticatorService{
    AppUserDAO authDAO;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    ProjectMapper mapper;
    JwtService jwtService;

    public AuthenticatorServiceImpl(AppUserDAO authDAO, ProjectMapper mapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authDAO = authDAO;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return LoginResponse.builder().token(token).build();


    }

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        AppUser appUser = mapper.map(request, AppUser.class);
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        authDAO.save(appUser);
        return SignUpResponse.builder().username(appUser.getUsername()).message("Sign up successfully").build();
    }
}
