package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.entity.Auth;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AuthServiceImplTest {

    @Autowired
    AuthService authService;

    @PersistenceContext
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

    @AfterEach
    void after_each() { // 테스트 후에 데이터 삭제
        List<Auth> auths = em.createQuery("select a from Auth a", Auth.class)
                .getResultList();
        for (Auth auth : auths) {
            em.remove(auth);
        }
    }


    @Test
    void updateAuthPassword() {
        AuthUpdatePasswordReqeustDto dto = new AuthUpdatePasswordReqeustDto();
        dto.setPassword("password1234");


        String email = "test@naver.com";
        Auth auth = em.createQuery("select a from Auth a where a.email =: email", Auth.class)
                .setParameter("email", email)
                .getSingleResult();


        String passwordBefore = auth.getPassword();


        // token이 없어서 수정
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.setPassword(encoder.encode(dto.getPassword()));


        Auth updated = em.createQuery("select a from Auth a where a.email =: email", Auth.class)
                .setParameter("email", email)
                .getSingleResult();

        String passwordUpdated = updated.getPassword();

        System.out.println("passwordBefore = " + passwordBefore);
        System.out.println("passwordUpdated = " + passwordUpdated);

        assertThat(passwordBefore).isNotSameAs(passwordUpdated);
    }

    // login test
}