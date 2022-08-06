package com.todoay.api.global.customValidation.annotation;

import com.todoay.api.global.customValidation.validator.CategoryNameValidator;
import com.todoay.api.global.customValidation.validator.EmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryNameValidator.class)
public @interface ValidationCategoryName {
    String message() default "카테고리 이름은 null이거나 공백일 수 없습니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
