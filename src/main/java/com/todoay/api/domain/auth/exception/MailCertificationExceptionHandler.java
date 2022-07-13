package com.todoay.api.domain.auth.exception;

import com.todoay.api.domain.auth.controller.MailCertificationController;
import com.todoay.api.domain.auth.service.MailCertificationService;
import com.todoay.api.global.exception.ErrorCode;
import com.todoay.api.global.exception.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackageClasses = {MailCertificationController.class, MailCertificationService.class})
public class MailCertificationExceptionHandler {

    @ExceptionHandler(EmailFormatInvalidException.class)
    public ResponseEntity<?> handleOutOfFormatException(EmailFormatInvalidException exception) {
        return ErrorResponse.toResponseEntity(EErrorCode.EMAIL_FORMAT_INVALID);
    }

    @Getter
    @RequiredArgsConstructor
    private enum EErrorCode implements ErrorCode {
        EMAIL_FORMAT_INVALID(BAD_REQUEST, "email format is not valid")

        ;
        private final HttpStatus httpStatus;
        private final String detailMessage;
    }

}
