package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.*;

public interface MailVerificationService {
    String sendVerificationMail(AuthSendEmailRequestDto authSendEmailRequestDto);
    void verifyEmailOnSignUp(AuthVerifyEmailTokenOnSingUpDto authVerifyEmailTOkenOnSingUpDto);
    CheckEmailVerifiedResponseDto checkEmailVerified(String email);

    String sendUpdatePasswordMail(AuthSendUpdatePasswordMailRequestDto dto);

    String verifyEmailOnUpdatePassword(AuthVerifyEmailTokenOnUpdatePassword dto);
}