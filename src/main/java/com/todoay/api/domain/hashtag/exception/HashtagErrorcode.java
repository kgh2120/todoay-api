package com.todoay.api.domain.hashtag.exception;

import com.todoay.api.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor @Getter
public enum HashtagErrorcode implements ErrorCode {

    NO_MORE_DATA(HttpStatus.FORBIDDEN, "더 이상 검색할 수 있는 데이터가 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String detailMessage;
}
