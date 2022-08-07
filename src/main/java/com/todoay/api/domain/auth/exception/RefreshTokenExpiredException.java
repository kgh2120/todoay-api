package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorCode;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.REFRESH_TOKEN_EXPIRED;

public class RefreshTokenExpiredException extends AbstractApiException {
    public RefreshTokenExpiredException() {
        super(REFRESH_TOKEN_EXPIRED);
    }
}
