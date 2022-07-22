package com.todoay.api.global.exception;

public abstract class JwtException extends AbstractApiException{
    protected JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
