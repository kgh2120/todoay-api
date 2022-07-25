package com.todoay.api.domain.auth.service;


import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.LoginResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthService extends UserDetailsService {
    // spring security 필수 메소드

    void updateAuthPassword(AuthUpdatePasswordReqeustDto dto); // 비밀번호 변경

    // 계정 탈퇴
    void deleteAuth();
    Long save(AuthSaveDto authSaveDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    boolean isExistEmail(String email);
}
