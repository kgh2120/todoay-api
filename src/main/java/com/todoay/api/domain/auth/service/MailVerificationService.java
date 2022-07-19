package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import io.jsonwebtoken.Claims;

public interface MailVerificationService {
    void sendVerificationMail(EmailDto emailDto);
    Claims verifyEmailToken(EmailTokenDto emailTokenDto);
}