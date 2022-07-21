package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

//    private final AuthServiceImpl authServiceImpl;
    private final AuthService authService;

    @PostMapping("save")
    public AuthSaveDto signup(@RequestBody AuthSaveDto authSaveDto) {
        authService.save(authSaveDto);
        // save까지 authService interface에 구현?
        return authSaveDto;
    }
}
