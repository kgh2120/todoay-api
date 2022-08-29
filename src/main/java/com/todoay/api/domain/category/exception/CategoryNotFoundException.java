package com.todoay.api.domain.category.exception;

import com.todoay.api.domain.profile.exception.ProfileErrorCode;
import com.todoay.api.global.exception.AbstractApiException;

public class CategoryNotFoundException extends AbstractApiException {
    public CategoryNotFoundException() {
        super(CategoryErrorCode.CATEGORY_NOT_FOUND);
    }
}
