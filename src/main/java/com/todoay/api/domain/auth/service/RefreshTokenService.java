package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.LoginResponseDto;
import com.todoay.api.domain.auth.dto.RefreshRequestDto;
import com.todoay.api.domain.auth.dto.RefreshResponseDto;

public interface RefreshTokenService {
    // login 할 때 저장해주기.
    void login(LoginRequestDto dto, String refreshToken);
    // refresh 될 때 access token과 refreshtoken 재발급 해주기. 재발급 하면 기존에 있던 것을 삭제하나 아니면 업데이트하나..?

    RefreshResponseDto refreshTokens(RefreshRequestDto dto);

}
