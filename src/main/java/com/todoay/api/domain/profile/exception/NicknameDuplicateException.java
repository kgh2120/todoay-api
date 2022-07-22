package com.todoay.api.domain.profile.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class NicknameDuplicateException extends AbstractApiException {
    public NicknameDuplicateException() {
        super(ProfileErrorCode.NICKNAME_DUPLICATE);
    }
}
