package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.CNickname;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<CNickname, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank();
    }
}
