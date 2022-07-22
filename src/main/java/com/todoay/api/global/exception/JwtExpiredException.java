package com.todoay.api.global.exception;

import static com.todoay.api.global.exception.GlobalErrorCode.JWT_EXPIRED;

public class JwtExpiredException extends JwtException{
    public JwtExpiredException() {
        super(JWT_EXPIRED);
    }
}
