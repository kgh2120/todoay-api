package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class LoginUnmatchedException extends AbstractApiException {
    public LoginUnmatchedException(){super(AuthErrorCode.LOGIN_FAILED);}
}
