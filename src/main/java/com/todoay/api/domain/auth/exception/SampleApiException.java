package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.auth.exception.SampleErrorCode.*;

public class SampleApiException extends AbstractApiException {
    public SampleApiException() {
        super(SAMPLE_API_EXCEPTION);
    }
}
