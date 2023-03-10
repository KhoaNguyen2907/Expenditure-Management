package com.devper.authenticator.exception;

import com.devper.common.exception.GlobalExceptionHandler;
import com.devper.common.exception.NotFoundException;
import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleUsernameNotFoundException(UsernameNotFoundException exception){
        return ResponseUtils.errorNotFound(new NotFoundException(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseWrapper> handleBadCredentialsException(BadCredentialsException exception){
        return ResponseUtils.errorNotFound(new NotFoundException("Wrong password"), HttpStatus.NOT_FOUND);
    }

}
