package com.todoay.api.global.customValidation.annotation;

import com.todoay.api.global.customValidation.validator.NicknameValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface CNickname {
    String message() default "닉네임은 null 또는 공백일 수 없습니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
