package com.todoay.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthSendUpdatePasswordMailRequestDto {
    @Email
    @NotBlank
    @Schema(required = true)
    private String email;
}
