package com.todoay.api.domain.todo.exception;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorCode;

import static com.todoay.api.domain.todo.exception.TodoErrorCode.ENUM_MISMATCH;

public class EnumMismatchException extends AbstractApiException {
    public EnumMismatchException() {
        super(ENUM_MISMATCH);
    }
}
