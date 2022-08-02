package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.CEmail;
import com.todoay.api.global.customValidation.annotation.CPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(required = true)  // swagger에 설명을 추가해주는
    @CEmail
    private String email;

    @CPassword
    private String password;

}
