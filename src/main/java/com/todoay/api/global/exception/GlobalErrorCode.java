package com.todoay.api.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
    ARGUMENT_FORMAT_INVALID(HttpStatus.BAD_REQUEST,"양식에 맞지 않은 입력값이 입력되었습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // v
    JWT_NOT_VERIFIED(HttpStatus.FORBIDDEN, "토큰의 시그내처가 유효하지 않습니다."), // v
    JWT_MALFORMED(HttpStatus.FORBIDDEN, "토큰의 형식이 잘못되었습니다. 토큰은 [header].[payload].[secret]의 형식이어야 합니다."), // v
    JWT_UNSUPPORTED(HttpStatus.FORBIDDEN, "지원하지 않는 종류의 토큰입니다."),
    JWT_HEADER_NOT_FOUND(HttpStatus.FORBIDDEN, "JWT을 담은 Request Header가 존재하지 않습니다."), // v

    SQL_INTEGRITY_CONSTRAINT_VIOLATION(HttpStatus.FORBIDDEN,"DB 제약조건을 위반하였습니다.")


    ;

    private final HttpStatus httpStatus;
    private final String detailMessage;

}
