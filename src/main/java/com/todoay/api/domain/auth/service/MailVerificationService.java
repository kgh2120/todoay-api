package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;

public interface MailVerificationService {
    void sendVerificationMail(EmailDto emailDto);
    void verifyEmailToken(EmailTokenDto emailTokenDto);
}