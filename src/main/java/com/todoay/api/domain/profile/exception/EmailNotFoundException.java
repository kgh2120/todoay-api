package com.todoay.api.domain.profile.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class EmailNotFoundException extends AbstractApiException {
    public EmailNotFoundException() {
        super(ProfileErrorCode.EMAIL_NOT_FOUND);
    }
}
