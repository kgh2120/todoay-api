package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.service.MailCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MailCertificationController {
    private final MailCertificationService mailCertificationService;

    @GetMapping("/auth/mail")
    public ResponseEntity<Void> sendCertificationMail(@Valid EmailDto emailDto) {
        mailCertificationService.sendEmail(emailDto);
        return ResponseEntity.noContent().build();
    }
}
