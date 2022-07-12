package com.todoay.api.domain.mailSender.controller;

import com.todoay.api.domain.mailSender.dto.MailDto;
import com.todoay.api.domain.mailSender.service.MailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MailSendingController {
    private final MailSendingService mailSendingService;

    @GetMapping("/mail")
    public void sendMail(MailDto mailDto) {
        mailSendingService.sendEmail(mailDto);
    }
}
