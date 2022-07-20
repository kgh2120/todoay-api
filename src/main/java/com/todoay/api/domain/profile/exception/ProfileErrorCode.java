package com.todoay.api.domain.profile.exception;


import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않은 이메일입니다."),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다.");



    private final HttpStatus httpStatus;
    private final String detailMessage;

}
