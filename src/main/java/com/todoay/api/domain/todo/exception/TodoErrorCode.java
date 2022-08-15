package com.todoay.api.domain.todo.exception;

import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.annotation.processing.Generated;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode implements ErrorCode {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "Todo를 찾을 수 없습니다."),
    NOT_YOUR_TODO(HttpStatus.FORBIDDEN, "로그인 유저의 리소스가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String detailMessage;
}
