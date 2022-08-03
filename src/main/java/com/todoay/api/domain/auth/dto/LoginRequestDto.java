package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.ValidationEmail;
import com.todoay.api.global.customValidation.annotation.ValidationPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(required = true)  // swagger에 설명을 추가해주는
    @ValidationEmail
    private String email;

    @ValidationPassword
    private String password;

}
