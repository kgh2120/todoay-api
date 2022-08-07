package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.ValidationEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthSendUpdatePasswordMailRequestDto {
    @ValidationEmail
    @Schema(required = true)
    private String email;
}
