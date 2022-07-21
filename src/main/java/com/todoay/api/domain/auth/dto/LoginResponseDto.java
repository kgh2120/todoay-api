package com.todoay.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    // access token
    private String accessToken;

    // refresh token
    private String refreshToken;
}
