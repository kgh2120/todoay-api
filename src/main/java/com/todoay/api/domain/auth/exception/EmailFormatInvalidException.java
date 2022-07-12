package com.todoay.api.domain.auth.exception;

public class EmailFormatInvalidException extends IllegalArgumentException {
    public EmailFormatInvalidException(String message) {
        super(message);
    }
}
