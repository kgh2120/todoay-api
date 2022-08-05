package com.todoay.api.domain.hashtag.exception;

import com.todoay.api.global.exception.AbstractApiException;

import static com.todoay.api.domain.hashtag.exception.HashtagErrorcode.NO_MORE_DATA;

public class NoMoreDataException extends AbstractApiException {
    public NoMoreDataException() {
        super(NO_MORE_DATA);
    }
}
