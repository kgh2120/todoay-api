package com.todoay.api.domain.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SampleErrorCode {

    SAMPLE_API_EXCEPTION(HttpStatus.BAD_REQUEST, "샘플 APIException","예시로 터트린 예외");




    private final HttpStatus httpStatus;
    private final String name;
    private final String detailMessage;


}
