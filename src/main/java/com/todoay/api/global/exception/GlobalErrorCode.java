package com.todoay.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{


    ARGUMENT_FORMAT_INVALID(HttpStatus.BAD_REQUEST,"양식에 맞지 않은 입력값이 입력되었습니다.");

    private final HttpStatus httpStatus;
    private final String detailMessage;

}
