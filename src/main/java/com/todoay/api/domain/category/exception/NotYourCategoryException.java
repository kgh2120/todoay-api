package com.todoay.api.domain.category.exception;

import com.todoay.api.global.exception.AbstractApiException;

public class NotYourCategoryException extends AbstractApiException {
    public NotYourCategoryException() {
        super(CategoryErrorCode.NOT_YOUR_CATEGORY);
    }
}
