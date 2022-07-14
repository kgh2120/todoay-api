package com.todoay.api.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidDetail {
    private final String code;
    private final String field;
    private final Object rejectedValue;
    private final String message;

    @Builder
    public ValidDetail(String code, String field, Object rejectedValue, String message) {
        this.code = code;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
