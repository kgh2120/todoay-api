package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MailVerificationServiceImplTest {

    @Autowired
    AuthService authService;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void before_each() {
        AuthSaveDto dto = new AuthSaveDto();
        dto.setEmail("test@naver.com");
        dto.setNickname("tester");
        dto.setPassword("12341234");

        AuthSaveDto dto2 = new AuthSaveDto();
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
        String email = "test@naver.com";


        // when
        Auth auth = authRepository.findByEmail(email).get();
        auth.completeEmailVerification();

        // then
        Assertions.assertNotNull(auth.getEmailVerifiedAt());
    }
}