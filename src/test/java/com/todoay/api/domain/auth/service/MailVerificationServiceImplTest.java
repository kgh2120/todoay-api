package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthSendEmailRequestDto;
import com.todoay.api.domain.auth.dto.AuthVerifyEmailTokenOnSingUpRequestDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MailVerificationServiceImplTest {
    @Autowired
    AuthService authService;

    @Autowired
    MailVerificationService mailVerificationService;

    @Autowired
    AuthRepository authRepository;

    AuthSaveDto dto;
    AuthSaveDto dto2;

    @BeforeEach
    void before_each() {
        dto = new AuthSaveDto();
        dto.setEmail("test1234@naver.com");
        dto.setNickname("tester1234");
        dto.setPassword("12341234");

        dto2 = new AuthSaveDto();
        dto2.setEmail("test2@naver.com");
        dto2.setNickname("tester2");
        dto2.setPassword("22222222");

        authService.save(dto);
        authService.save(dto2);
    }

    @Test
    void verifyEmail() {
        // given
        // beforeEach
        String email = dto.getEmail();

        // when
        String emailToken = mailVerificationService.sendVerificationMail(AuthSendEmailRequestDto.builder().email(email).build());
        mailVerificationService.verifyEmailOnSignUp(AuthVerifyEmailTokenOnSingUpRequestDto.builder().emailToken(emailToken).build());

        // then
        Auth auth = authRepository.findByEmail(email).get();
        Assertions.assertNotNull(auth.getEmailVerifiedAt());
    }

    @Test
    void checkEmailVerified() {
        // given
        // beforeEach
        String email1 = dto.getEmail();

        // when
        boolean emailVerified1 = mailVerificationService.checkEmailVerified(email1).isEmailVerified();

        // then
        Assertions.assertEquals(emailVerified1, false);
    }
}