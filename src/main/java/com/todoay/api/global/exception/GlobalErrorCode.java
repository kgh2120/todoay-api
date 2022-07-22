package com.todoay.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
    ARGUMENT_FORMAT_INVALID(HttpStatus.BAD_REQUEST,"양식에 맞지 않은 입력값이 입력되었습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "이메일 토큰이 만료되었습니다."),
    JWT_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, "토큰의 시그내처가 유효하지 않습니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "토큰의 형식이 잘못되었습니다. 토큰은 [header].[payload].[secret]의 형식이어야 합니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원하지 않는 종류의 토큰입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detailMessage;

}
