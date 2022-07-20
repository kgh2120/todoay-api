package com.todoay.api.domain.profile.controller;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @Value("header.access-token")
    private String accessToken;

    @GetMapping("/profile/my")
    public ResponseEntity<ProfileReadResponseDto> getMyProfile(HttpServletRequest request) {

        String email = getJwtTokenByHeader(request);
        // TODO jwt를 뜯어줘야 함.
        ProfileReadResponseDto profile = profileService.readMyProfile(email);

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile/my")
    public ResponseEntity updateProfile(HttpServletRequest request, @RequestBody @Validated ProfileUpdateReqeustDto dto) {

        String email = getJwtTokenByHeader(request);
        // TODO jwt를 뜯어줘야 함.
        profileService.updateMyProfile(email, dto);

        return ResponseEntity.status(204).build();
    }

    private String getJwtTokenByHeader(HttpServletRequest request) { // jwt 관련 클래스가 있다면 거기로 이전하는 것이 좋아보임.
        String token = request.getHeader(accessToken);
        return token;
    }
}
