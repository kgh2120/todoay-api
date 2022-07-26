package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.EMAIL_NOT_VERIFIED;

public class EmailNotVerifiedException extends AbstractApiException {
    public EmailNotVerifiedException() {
        super(EMAIL_NOT_VERIFIED);
    }
}
