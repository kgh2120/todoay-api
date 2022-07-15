package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.Dto.AuthSaveDto;
import com.todoay.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public String signup(AuthSaveDto authSaveDto) {
        authService.save(authSaveDto);
        return "redirect:/auth";
    }
}
