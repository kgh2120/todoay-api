package com.todoay.api.domain.profile.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.profile.exception.ProfileErrorCode.FILE_TYPE_MISMATCH;

public class FileTypeMismatchException extends AbstractApiException {
    public FileTypeMismatchException() {
        super(FILE_TYPE_MISMATCH);
    }
}
