package com.todoay.api.domain.todo.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class BadRepeatConditionException extends AbstractApiException {
    public BadRepeatConditionException() {
        super(TodoErrorCode.BAD_REPEAT_CONDITION);
    }
}
