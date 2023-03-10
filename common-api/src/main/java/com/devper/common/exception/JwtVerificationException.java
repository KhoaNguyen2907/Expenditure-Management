package com.devper.common.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtVerificationException extends AuthenticationException {

    public JwtVerificationException(String message) {
        super(message);
    }

    public JwtVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
