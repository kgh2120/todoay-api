package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.CEmail;
import lombok.Data;

@Data
public class AuthEmailExistsReqeustDto {
    @CEmail
    private String email;
}
