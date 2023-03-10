package com.devper.authenticator.controller;

import com.devper.authenticator.model.request.LoginRequest;
import com.devper.authenticator.model.request.SignUpRequest;
import com.devper.authenticator.model.response.LoginResponse;
import com.devper.authenticator.model.response.SignUpResponse;
import com.devper.authenticator.service.AuthenticatorService;
import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateController {
    @Autowired
    AuthenticatorService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseWrapper> signUp(@Valid @RequestBody SignUpRequest request) {
        log.info("signUp: {}", request);
        SignUpResponse response = authService.signUp(request);
        return ResponseUtils.success(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(@Valid @RequestBody LoginRequest request) {
       log.info("login: {}", request);
       LoginResponse response = authService.login(request);
       return ResponseUtils.success(response, HttpStatus.OK);
    }
}
