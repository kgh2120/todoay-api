package com.todoay.api.global.exception;

import static com.todoay.api.global.exception.GlobalErrorCode.JWT_MALFORMED;

public class JwtMalformedException extends JwtException{
    public JwtMalformedException() {
        super(JWT_MALFORMED);
    }
}
