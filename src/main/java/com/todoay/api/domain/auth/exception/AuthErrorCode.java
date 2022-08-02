package com.todoay.api.domain.auth.exception;

import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {


    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    LOGIN_FAILED(HttpStatus.NOT_FOUND, "로그인에 실패하였습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "이메일 인증이 완료되지 않았습니다."),
    LOGIN_DELETED_ACCOUNT(HttpStatus.FORBIDDEN,"삭제된 상태의 계정으로 로그인 시도했습니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.NOT_FOUND,"올바르지 않은 비밀번호를 입력하였습니다."),

    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST,"리프래쉬 토큰이 만료되었습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "전달하신 RefreshToken은 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detailMessage;


}
