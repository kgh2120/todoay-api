package com.todoay.api.domain.todo.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class TodoNotFoundException extends AbstractApiException {
    public TodoNotFoundException() {
        super(TodoErrorCode.TODO_NOT_FOUND);
    }
}
