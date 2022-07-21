package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.ditio.EmailDto;
import com.todoay.api.domain.auth.ditio.EmailTokenDto;

public interface MailVerificationService {
    void sendVerificationMail(EmailDto emailDto);
    void verifyEmailToken(EmailTokenDto emailTokenDto);
}