package com.devper.common.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleNotFoundException(NotFoundException exception) {
        log.error("Not Found Exception \n {}", exception.getMessage());
        return ResponseUtils.errorNotFound(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("Constraint Violation Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorConstraint(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Method Argument Not Valid Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorMethodArgument(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ResponseWrapper> handleJWTVerificationException(JWTVerificationException exception) {
        log.error("JWT Verification Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorJWTVerification(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ResponseWrapper> handleJWTDecodeException(JWTDecodeException exception) {
        log.error("JWT Decode Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorJWTVerification(exception, HttpStatus.BAD_REQUEST);
    }


}
