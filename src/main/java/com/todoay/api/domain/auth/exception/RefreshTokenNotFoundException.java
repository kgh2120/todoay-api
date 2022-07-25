package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorCode;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.REFRESH_TOKEN_NOT_FOUND;

public class RefreshTokenNotFoundException extends AbstractApiException {

    public RefreshTokenNotFoundException() {
        super(REFRESH_TOKEN_NOT_FOUND);
    }
}
