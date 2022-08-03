package com.todoay.api.global.customValidation.annotation;

import com.todoay.api.global.customValidation.validator.PasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidationPassword {
    String message() default "비밀번호는 null이거나 공백일 수 없으며 비밀번호 형식을 따라야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
