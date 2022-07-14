package com.todoay.api.global.config;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidDetail;
import com.todoay.api.global.exception.ValidErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleAbstractApiException(AbstractApiException e, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(e, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpServletRequest request) {
        List<ValidDetail> details = ex.getBindingResult().getFieldErrors().stream().map(e ->
                ValidDetail.builder()
                        .code(e.getCode())
                        .field(e.getField())
                        .rejectedValue(e.getRejectedValue())
                        .message(e.getDefaultMessage())
                        .build()
        ).collect(Collectors.toList());
        return ValidErrorResponse.toResponseEntity(details, request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidErrorResponse> handleBindException(BindException ex,HttpServletRequest request) {
        return ValidErrorResponse.toResponseEntity(ex, request.getRequestURI());
    }
}
