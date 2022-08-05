package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryNameValidator implements ConstraintValidator<ValidationCategoryName, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank();
    }
}
