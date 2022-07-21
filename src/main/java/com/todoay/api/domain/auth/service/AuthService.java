package com.todoay.api.domain.auth.service;


import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.dto.AuthSaveDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthService extends UserDetailsService {
    // spring security 필수 메소드
    public Long save(AuthSaveDto authSaveDto);

    void updateAuthPassword(String email, AuthUpdatePasswordReqeustDto dto); // 비밀번호 변경

    // 계정 탈퇴
    void deleteAuth(String email);
}
