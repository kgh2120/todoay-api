package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.ValidationPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidationPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&()-])(?=.*[a-zA-Z]).{8,20}$");
    }
}
