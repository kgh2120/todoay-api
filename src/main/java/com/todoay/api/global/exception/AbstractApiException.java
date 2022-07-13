package com.todoay.api.global.exception;

import org.springframework.http.HttpStatus;

public class AbstractApiException extends RuntimeException implements ErrorCode{

    private final HttpStatus httpStatus;
    private final String name;
    private final String detailMessage;

    public AbstractApiException(HttpStatus httpStatus, String name, String detailMessage) {
        this.httpStatus = httpStatus;
        this.name = name;
        this.detailMessage = detailMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String getDetailMessage() {
        return this.detailMessage;
    }
}
