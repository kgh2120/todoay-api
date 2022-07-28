package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.LOGIN_DELETED_ACCOUNT;

public class LoginDeletedAccountException extends AbstractApiException {
    public LoginDeletedAccountException() {
        super(LOGIN_DELETED_ACCOUNT);
    }
}
