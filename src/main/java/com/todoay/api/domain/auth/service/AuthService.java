package com.todoay.api.domain.auth.service;


import com.todoay.api.domain.auth.Dto.AuthSaveDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public interface AuthService extends UserDetailsService {
    // spring security 필수 메소드
    public Long save(AuthSaveDto authSaveDto);
}
