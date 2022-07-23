package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorCode;

import static com.todoay.api.domain.auth.exception.AuthErrorCode.EMAIL_DUPLICATE;

public class EmailDuplicateException extends AbstractApiException {
    public EmailDuplicateException() {
        super(EMAIL_DUPLICATE);
    }
}
