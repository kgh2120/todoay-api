package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.CEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthSendEmailRequestDto {
    @Schema(required = true)
    @CEmail
    private String email;
}
