package com.todoay.api.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    String name();
    String getDetailMessage();
}
