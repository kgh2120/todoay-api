package com.todoay.api.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidErrorResponse implements ErrorCode{

    private final HttpStatus httpStatus;
    private final String name;
    private final String detailMessage;
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final String path;
    private final List<ValidDetail> details = new ArrayList<>();


    public ValidErrorResponse(String path) {
        httpStatus = HttpStatus.BAD_REQUEST;
        name = "ARGUMENT_FORMAT_INVALID";
        detailMessage = "양식에 맞지 않은 입력값이 입력되었습니다.";
        this.path = path;
    }

    public void addDetail(ValidDetail detail) {
        this.details.add(detail);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }
}
