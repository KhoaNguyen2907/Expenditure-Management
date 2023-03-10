package com.devper.authenticator.service;

import com.devper.authenticator.model.request.LoginRequest;
import com.devper.authenticator.model.request.SignUpRequest;
import com.devper.authenticator.model.response.LoginResponse;
import com.devper.authenticator.model.response.SignUpResponse;

public interface AuthenticatorService {

    LoginResponse login(LoginRequest request);

    SignUpResponse signUp(SignUpRequest request);
}
