package com.todoay.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailTokenDto {
    @NotBlank
    @Schema(description = "이메일 인증 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Indsc2R5ZDA1MDdAbmF2ZXIuY29tIiwiZXhwIjoxNjU4MTMyNDU2fQ.LOSJBTTZjoa2EnPuSO8uHvgGp9VYN31YmBdxNiGd41w")
    private String emailToken;
}
