package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.ValidationEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidationEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null &&
                !value.isBlank() &&
                value.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
    }
}
