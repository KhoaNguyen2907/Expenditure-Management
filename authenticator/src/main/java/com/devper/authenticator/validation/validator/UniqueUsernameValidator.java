package com.devper.authenticator.validation.validator;

import com.devper.authenticator.dao.AuthenticatorDAO;
import com.devper.authenticator.model.AppUser;
import com.devper.authenticator.validation.annotation.UniqueUsername;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    AuthenticatorDAO dao;
    String message;
    public UniqueUsernameValidator() {
    }
    public void initialize(UniqueUsername constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }
    public boolean isValid(String value, ConstraintValidatorContext context) {
        AppUser user = dao.findByUsername(value).orElse(null);
        if (user != null) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation().disableDefaultConstraintViolation();
        }
        return user == null;
    }
}