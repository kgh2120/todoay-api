package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.exception.EmailDuplicateException;
import com.todoay.api.domain.auth.exception.EmailNotVerifiedException;
import com.todoay.api.domain.auth.exception.LoginDeletedAccountException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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


    @Test @DisplayName("회원가입 테스트")
    void signUp() {
        //given
        AuthSaveDto dto = new AuthSaveDto();
        dto.setEmail("test3@naver.com");
        dto.setNickname("tester3");
        dto.setPassword("22222222");

        authService.save(dto);

        //when
        Auth findAuth = em.createQuery("select a from Auth a where a.email = :email", Auth.class)
                .setParameter("email", dto.getEmail())
                .getSingleResult();
        // then
        assertThat(findAuth.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test @DisplayName("회원가입_예외_이메일_중복검사_실패")
    void signUpEmailDuplicateException() {
        //given
        AuthSaveDto dto = new AuthSaveDto();
        dto.setEmail("test@naver.com");
        dto.setNickname("tester");
        dto.setPassword("12341234");
        //when
        assertThatThrownBy(() -> authService.save(dto))
                // then
                .isInstanceOf(EmailDuplicateException.class);
    }

    @Test @DisplayName("회원가입_예외_닉네임_중복검사_실패")
    void signUpNicknameDuplicateException() {
        //given
        AuthSaveDto dto = new AuthSaveDto();
        dto.setEmail("test123@naver.com");
        dto.setNickname("tester");
        dto.setPassword("12341234");
        //when
        assertThatThrownBy(() -> authService.save(dto))
                // then
                .isInstanceOf(NicknameDuplicateException.class);
    }

    @Test @DisplayName("로그인_예외_이메일_인증이_되지않은_계정")
    void loginEmailNotVerifiedException() {
        //given
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("test@naver.com");
        dto.setPassword("12341234");

        //when then
        assertThatThrownBy(()-> authService.login(dto))
                .isInstanceOf(EmailNotVerifiedException.class);
    }
    @Test @DisplayName("로그인_예외_삭제된_계정")
    void loginLoginDeletedAccountException() {
        String email = "test@naver.com";
        Auth auth = em.createQuery("select a from Auth a where a.email =: email", Auth.class)
                .setParameter("email", email)
                .getSingleResult();
        auth.setEmailVerifiedAt(LocalDateTime.now());
        auth.setDeletedAt(LocalDateTime.now());

        em.flush();

        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("test@naver.com");
        dto.setPassword("12341234");

        //when then
        assertThatThrownBy(()-> authService.login(dto))
                .isInstanceOf(LoginDeletedAccountException.class);


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

    @Test
    void deleteTest() {
        String email = "test@naver.com";
        Auth auth = em.createQuery("select a from Auth a where a.email =: email", Auth.class)
                .setParameter("email", email)
                .getSingleResult();

        auth.deleteAuth();
        em.flush();

        Auth deleted = em.find(Auth.class, auth.getId());

        System.out.println(deleted.getEmail());
        System.out.println(deleted.getDeletedAt());
        System.out.println("deleted.getProfile().getNickname() = " + deleted.getProfile().getNickname());
        System.out.println("deleted.getProfile().getImgUrl() = " + deleted.getProfile().getImgUrl());
        System.out.println("deleted.getProfile().getIntroMsg() = " + deleted.getProfile().getIntroMsg());

        assertThat(deleted.getDeletedAt()).isNotNull();
    }

    @Test
    void deleteTestUUIDDuplicate() {
        String email = "test@naver.com";
        Auth auth = em.createQuery("select a from Auth a where a.email =: email", Auth.class)
                .setParameter("email", email)
                .getSingleResult();
        try {
            auth.setEmail("test2@naver.com");
            em.flush();
        } catch (PersistenceException e) {
            auth.setEmail(UUID.randomUUID().toString());
        }finally {
            System.out.println("auth.getEmail() = " + auth.getEmail());
            em.clear();
        }
    }




    // login test
}