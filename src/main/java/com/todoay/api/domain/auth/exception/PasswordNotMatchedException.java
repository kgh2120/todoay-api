package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorCode;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.PASSWORD_NOT_MATCHED;

public class PasswordNotMatchedException extends AbstractApiException {
    public PasswordNotMatchedException() {
        super(PASSWORD_NOT_MATCHED);
    }
}
