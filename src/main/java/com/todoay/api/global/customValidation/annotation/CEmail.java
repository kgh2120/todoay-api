package com.todoay.api.global.customValidation.annotation;

import com.todoay.api.global.customValidation.validator.EmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface CEmail {
    String message() default "이메일은 null이거나 공백일 수 없으며 이메일 형식을 따라야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
