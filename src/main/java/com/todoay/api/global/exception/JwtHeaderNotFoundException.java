package com.todoay.api.global.exception;

import static com.todoay.api.global.exception.GlobalErrorCode.JWT_HEADER_NOT_FOUND;

public class JwtHeaderNotFoundException extends JwtException{
    public JwtHeaderNotFoundException() {
        super(JWT_HEADER_NOT_FOUND);
    }
}
