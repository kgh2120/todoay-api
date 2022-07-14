package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SampleErrorCode implements ErrorCode {

    SAMPLE_API_EXCEPTION(HttpStatus.BAD_REQUEST, "예시로 터트린 예외");

    private final HttpStatus httpStatus;
    private final String detailMessage;

}
