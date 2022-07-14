package com.todoay.api.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractApiException extends RuntimeException implements ErrorCode{

    private final HttpStatus httpStatus;
    private final String name;
    private final String detailMessage;

    public AbstractApiException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.name = errorCode.name();
        this.detailMessage = errorCode.getDetailMessage();
    }

    @Override
    public String name() {
        return name;
    }
}
