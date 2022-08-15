package com.todoay.api.domain.todo.exception;

import com.todoay.api.domain.category.exception.CategoryErrorCode;
import com.todoay.api.global.exception.AbstractApiException;

public class NotYourTodoException extends AbstractApiException {
    public NotYourTodoException() {
        super(TodoErrorCode.NOT_YOUR_TODO);
    }
}
