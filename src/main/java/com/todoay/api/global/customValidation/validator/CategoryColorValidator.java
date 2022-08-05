package com.todoay.api.global.customValidation.validator;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryColor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryColorValidator implements ConstraintValidator<ValidationCategoryColor, String> {
    private static String categoryColorRegex = "/^(#?([a-f\\d]{3,4}|[a-f\\d]{6}|[a-f\\d]{8})|rgb\\((0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d)\\)|rgba\\((0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|255|25[0-4]|2[0-4]\\d|1\\d\\d|0?\\d?\\d),(0|0?\\.\\d|1(\\.0)?)\\)|hsl\\((0|360|35\\d|3[0-4]\\d|[12]\\d\\d|0?\\d?\\d),(0|100|\\d{1,2})%,(0|100|\\d{1,2})%\\)|hsla\\((0|360|35\\d|3[0-4]\\d|[12]\\d\\d|0?\\d?\\d),(0|100|\\d{1,2})%,(0|100|\\d{1,2})%,(0?\\.\\d|1(\\.0)?)\\))$/\n";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank()
                && value.matches(categoryColorRegex);
    }
}
