package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import io.jsonwebtoken.Claims;

public interface MailVerificationService {
    String sendVerificationMail(EmailDto emailDto);
    void verifyEmail(EmailTokenDto emailTokenDto);
}