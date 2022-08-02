package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthVerifyEmailTokenOnSingUpRequestDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.global.jwt.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MailVerificationControllerTest {
    @Autowired
    AuthService authService;

    @Autowired
    MailVerificationController mailVerificationController;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    JwtProvider jwtProvider;

    AuthSaveDto dto1;
    AuthSaveDto dto2;

    @BeforeEach
    void before_each() {
        dto1 = new AuthSaveDto();
        dto1.setEmail("test1234@naver.com");
        dto1.setNickname("tester1234");
        dto1.setPassword("12341234");

        dto2 = new AuthSaveDto();
        dto2.setEmail("test2@naver.com");
        dto2.setNickname("tester2");
        dto2.setPassword("22222222");

        authService.save(dto1);
        authService.save(dto2);
    }

    @Test
    void verifyEmailTokenOnSignUp() {
        // given
        // beforeEach
        String email = dto1.getEmail();
        String emailToken = jwtProvider.createEmailToken(email);
        // 테스트 하려면 만료된 토큰 생성하는 것이 필요할 듯. JwtTokenProvider::createToken을 public으로 바꾸면 가능.

        // when
        mailVerificationController.verifyEmailTokenOnSignUp(AuthVerifyEmailTokenOnSingUpRequestDto.builder().emailToken(emailToken).build());
        Auth auth = authRepository.findByEmail(email).get();

        // then
        Assertions.assertNotNull(auth.getEmailVerifiedAt());
    }
}