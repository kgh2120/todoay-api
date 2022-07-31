package com.todoay.api.domain.auth.repository;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.entity.Auth;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Transactional
class AuthRepositoryTest {


    @Autowired AuthRepository authRepository;

    @BeforeEach
    void before_each() {
        AuthSaveDto dto = new AuthSaveDto();
        dto.setEmail("test1234@naver.com");
        dto.setNickname("tester1234");
        dto.setPassword("12341234");

        AuthSaveDto dto2 = new AuthSaveDto();
        dto2.setEmail("test2@naver.com");
        dto2.setNickname("tester2");
        dto2.setPassword("22222222");

        Auth auth1 = dto.toAuthEntity();

        Auth auth2 = dto2.toAuthEntity();

        authRepository.save(auth1);
        authRepository.save(auth2);
    }

    @Test
    void findByNickname() {

        //when
        String nickname = "tester1234";
        Auth tester = authRepository.findByNickname(nickname).get();

        //then

        assertThat(tester.getEmail()).isEqualTo("test1234@naver.com");

    }
}