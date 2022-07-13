package com.todoay.api.domain.auth.exception;

import com.todoay.api.domain.auth.controller.MailCertificationController;
import com.todoay.api.global.exception.ErrorCode;
import com.todoay.api.global.exception.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackageClasses = MailCertificationController.class)
public class MailCertificationExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(BindException bindException, HttpServletRequest request) {
        FieldError fieldError = bindException.getBindingResult().getFieldError();
        String validationCode = fieldError.getCode();
        EErrorCode errorCode = EErrorCode.UNHANDLED;
        switch (validationCode) {
            case "NotBlank":
                errorCode = EErrorCode.BLANK_NOT_ALLOWED;
                break;
            case "Email":
                errorCode = EErrorCode.EMAIL_NOT_VALID;
                break;
        }
        return ErrorResponse.toResponseEntity(errorCode, request.getRequestURI());
    }

    @Getter
    @RequiredArgsConstructor
    private enum EErrorCode implements ErrorCode {
        EMAIL_NOT_VALID(BAD_REQUEST, "email format is not valid"),
        UNHANDLED(INTERNAL_SERVER_ERROR, "unhandled error occurred"),
        BLANK_NOT_ALLOWED(BAD_REQUEST, "The param must not be blank, but it is null")
        ;
        private final HttpStatus httpStatus;
        private final String detailMessage;

        EErrorCode get(String message) {
            for (EErrorCode value : values()) {
                if(value.detailMessage.equals(message)) return value;
            }
            return null;
        }

    }

}
