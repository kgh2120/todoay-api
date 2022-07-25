package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.AuthSendEmailRequestDto;
import com.todoay.api.domain.auth.dto.AuthVerifyEmailTokenOnSingUpDto;
import com.todoay.api.domain.auth.dto.CheckEmailVerifiedResponseDto;

public interface MailVerificationService {
    String sendVerificationMail(AuthSendEmailRequestDto authSendEmailRequestDto);
    void verifyEmail(AuthVerifyEmailTokenOnSingUpDto authVerifyEmailTOkenOnSingUpDto);

    CheckEmailVerifiedResponseDto checkEmailVerified(String email);
}