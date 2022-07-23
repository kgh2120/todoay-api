package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSendEmailRequestDto;
import com.todoay.api.domain.auth.dto.AuthVerifyEmailTokenOnSingUpDto;

public interface MailVerificationService {
    String sendVerificationMail(AuthSendEmailRequestDto authSendEmailRequestDto);
    void verifyEmail(AuthVerifyEmailTokenOnSingUpDto authVerifyEmailTOkenOnSingUpDto);
}