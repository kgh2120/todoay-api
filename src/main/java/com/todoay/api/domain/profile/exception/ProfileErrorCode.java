package com.todoay.api.domain.profile.exception;


import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않은 이메일입니다."),
    NICKNAME_DUPLICATE(HttpStatus.FORBIDDEN, "이미 사용 중인 닉네임입니다."),

    FILE_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "이미지 파일이 아닌 파일을 업로드 했습니다."),
    
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IO 리소스 문제 발생"),

    SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 사이즈 초과")
    ;



    private final HttpStatus httpStatus;
    private final String detailMessage;

}
