package com.todoay.api.global.config;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidDetail;
import com.todoay.api.global.exception.ValidErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleAbstractApiException(AbstractApiException e) {
        return ErrorResponse.toResponseEntity(e);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidErrorResponse response = new ValidErrorResponse();

        ex.getBindingResult().getFieldErrors().forEach(e ->
            response.addDetail(ValidDetail.builder()
                    .code(e.getCode())
                    .field(e.getField())
                    .rejectedValue(e.getRejectedValue())
                    .message(e.getDefaultMessage())
                    .build())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
