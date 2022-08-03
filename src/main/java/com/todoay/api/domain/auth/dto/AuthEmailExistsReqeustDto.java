package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.ValidationEmail;
import lombok.Data;

@Data
public class AuthEmailExistsReqeustDto {
    @ValidationEmail
    private String email;
}
