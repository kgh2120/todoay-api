package com.todoay.api.global.exception;

import static com.todoay.api.global.exception.GlobalErrorCode.JWT_MALFORMED;

public class JwtUnsupportedException extends JwtException{
    public JwtUnsupportedException() {
        super(JWT_MALFORMED);
    }
}
