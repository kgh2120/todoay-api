package com.todoay.api.domain.profile.repository;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.domain.profile.entity.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProfileRepositoryTest {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AuthService authService;


    @BeforeEach @Rollback(value = false)
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


    @Test @DisplayName("조인 쿼리 정상 작동 테스트")
    void findProfileByAuthEmail() {
        Profile profile = profileRepository.findProfileByAuthEmail("test@naver.com").get();


        System.out.println(profile.getNickname());
        System.out.println(profile.getAuth().getEmail());
        System.out.println(profile.getAuth().getPassword());

        assertThat(profile.getNickname()).isEqualTo("tester");
    }


}