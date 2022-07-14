package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import com.todoay.api.domain.auth.service.MainVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MailVerificationController {
    private final MainVerificationService mainVerificationService;

    @GetMapping("/auth/mail")
    public ResponseEntity<Void> sendVerificationMail(@Valid EmailDto emailDto) {
        mainVerificationService.sendverificationMail(emailDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/email-verification")
    public ResponseEntity<Void> verifyEmailToken(@RequestBody EmailTokenDto emailTokenDto) {
        mainVerificationService.verifyEmailToken(emailTokenDto);
        return ResponseEntity.noContent().build();
    }
}
