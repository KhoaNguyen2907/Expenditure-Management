package com.devper.common.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devper.common.exception.JwtVerificationException;
import com.devper.common.exception.NotFoundException;
import com.devper.common.model.response.ResponseWrapper;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ResponseUtils {
    public static ResponseEntity<ResponseWrapper> success(Object data, HttpStatus status) {
        ResponseWrapper responseWrapper = getResponse(data, status,null);
        return new ResponseEntity<>(responseWrapper, status);
    }

    private static ResponseWrapper getResponse(Object data, HttpStatus status, List<String> errors) {
        return ResponseWrapper.builder()
                .content(data)
                .hasErrors(errors != null && !errors.isEmpty())
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .status(status.value())
                .build();
    }

    public static ResponseEntity<ResponseWrapper> errorNotFound(NotFoundException exception, HttpStatus status) {
        List<String> errors = Arrays.asList(exception.getMessage());
        ResponseWrapper responseWrapper = getResponse(null, status, errors);
        return new ResponseEntity<>(responseWrapper, status);
    }

    public static ResponseEntity<ResponseWrapper> errorConstraint(ConstraintViolationException exception, HttpStatus status) {
        List<String> errors = exception.getConstraintViolations().stream().map(e -> e.getMessage()).collect(Collectors.toList());
        ResponseWrapper responseWrapper = getResponse(null, status, errors);
        return new ResponseEntity<>(responseWrapper, status);    }

    public static ResponseEntity<ResponseWrapper> errorMethodArgument(MethodArgumentNotValidException exception, HttpStatus status) {
        List<String> errors = exception.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        ResponseWrapper responseWrapper = getResponse(null, status, errors);
        return new ResponseEntity<>(responseWrapper, status);
    }

    public static ResponseEntity<ResponseWrapper> errorJWTVerification(JWTVerificationException exception, HttpStatus status) {
        List<String> errors = Arrays.asList(exception.getMessage());
        ResponseWrapper responseWrapper = getResponse(null, status, errors);
        return new ResponseEntity<>(responseWrapper, status);
    }
}
