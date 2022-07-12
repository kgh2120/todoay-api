package com.todoay.api.domain.mailSender.controller;

import com.todoay.api.domain.mailSender.dto.MailDto;
import com.todoay.api.domain.mailSender.service.MailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailSendingController {
    private final MailSendingService mailSendingService;

    @GetMapping("/mail")
    public void sendMail(String email) {
        mailSendingService.sendEmail(email);
    }
}
