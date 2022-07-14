package com.todoay.api.global.config;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidDetail;
import com.todoay.api.global.exception.ValidErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleAbstractApiException(AbstractApiException e, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(e, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpServletRequest request) {
        ValidErrorResponse response = new ValidErrorResponse(request.getRequestURI());
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
