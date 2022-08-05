package com.todoay.api.domain.category.exception;

import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    NOT_YOUR_CATEGORY(HttpStatus.FORBIDDEN, "로그인 유저의 리소스가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String detailMessage;
}
