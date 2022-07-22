package com.todoay.api.global.exception;

import static com.todoay.api.global.exception.GlobalErrorCode.JWT_NOT_VERIFIED;

public class JwtNotVerifedException extends JwtException{
    public JwtNotVerifedException() {
        super(JWT_NOT_VERIFIED);
    }
}
