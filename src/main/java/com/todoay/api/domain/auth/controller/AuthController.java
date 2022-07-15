package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.Dto.AuthSaveDto;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.domain.profile.Dto.ProfileSaveDto;
import com.todoay.api.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ProfileService profileService;

    @PostMapping("/save")
    public String signup(@RequestBody AuthSaveDto authSaveDto, @RequestBody ProfileSaveDto profileSaveDto) {
        authService.save(authSaveDto);
        profileService.save(profileSaveDto);
        return "signup";
    }
}
