package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import com.todoay.api.domain.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest @Transactional
class ProfileServiceImplTest {

    @Autowired
    AuthService authService;

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository repository;

    @Autowired
    AuthRepository authRepository;


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

        authService.save(dto);
        authService.save(dto2);
        Auth auth = authRepository.findByEmail(dto.getEmail()).get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, "", auth.getAuthorities()));
    }





    @Test @DisplayName("프로필_업데이트_정상흐름")
    void updateMyProfile() {
        ProfileUpdateReqeustDto dto = new ProfileUpdateReqeustDto();
        dto.setNickname("테스터1");
        String introMsg = "안녕하세요 김테스터1입니다.";
        dto.setIntroMsg(introMsg);
        dto.setImageUrl("c/user/example/picture/image1.png");
        String email = "test1234@naver.com";

        profileService.updateMyProfile(dto);



        ProfileReadResponseDto profile = profileService.readMyProfile();
        System.out.println(profile);

        assertThat(profile.getIntroMsg()).isEqualTo(introMsg);

    }


    @Test @DisplayName("프로필 업데이트_예외_닉네임_중복")
    void updateMyProfile_Exception_NicknameDuplicateException() {
        ProfileUpdateReqeustDto dto = new ProfileUpdateReqeustDto();
        dto.setNickname("tester2");

        assertThatThrownBy(() -> profileService.updateMyProfile(dto))
                .isInstanceOf(NicknameDuplicateException.class);


    }
}