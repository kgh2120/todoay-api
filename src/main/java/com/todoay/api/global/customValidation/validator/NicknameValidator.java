package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.ValidationNickname;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<ValidationNickname, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank();
    }
}
