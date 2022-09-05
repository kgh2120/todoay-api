package com.todoay.api.domain.profile.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.profile.exception.ProfileErrorCode.FILE_UPLOAD_ERROR;

public class FileUploadErrorException extends AbstractApiException {
    public FileUploadErrorException() {
        super(FILE_UPLOAD_ERROR);
    }
}
