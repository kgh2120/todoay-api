package com.todoay.api.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidDetail {
    @Schema(example = "NotBlank")
    private final String code;
    @Schema(example = "email")
    private final String field;
    @Schema(example = "asdf@@gmail..com")
    private final Object rejectedValue;
    @Schema(example = "올바른 형식의 이메일 주소여야 합니다")
    private final String message;

    @Builder
    public ValidDetail(String code, String field, Object rejectedValue, String message) {
        this.code = code;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
