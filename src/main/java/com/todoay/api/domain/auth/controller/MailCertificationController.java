package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.exception.EmailFormatInvalidException;
import com.todoay.api.domain.auth.service.MailCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class MailCertificationController {
    private final MailCertificationService mailCertificationService;

    @GetMapping("/auth/mail")
    public ResponseEntity<Void> sendMail(@RequestParam String email) {
        if (!Pattern.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", email)) {
            throw new EmailFormatInvalidException("email format is invalid");
        }
        mailCertificationService.sendEmail(email);
        return ResponseEntity.noContent().build();
    }
}
