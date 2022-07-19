package com.todoay.api.global.config;

import com.todoay.api.global.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
    public ResponseEntity<ValidErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
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

    // GET params 는 MethodArgumentNoValidException 에 걸리지 않고 그 super class 인 BindException 에 걸림
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidErrorResponse> handleBindException(BindException ex, HttpServletRequest request) {
        return ValidErrorResponse.toResponseEntity(ex, request.getRequestURI());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(GlobalErrorCode.JWT_EXPIRED, request.getRequestURI());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(GlobalErrorCode.JWT_NOT_VERIFIED, request.getRequestURI());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(GlobalErrorCode.JWT_MALFORMED, request.getRequestURI());
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?> handleUnsupportedJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(GlobalErrorCode.JWT_UNSUPPORTED, request.getRequestURI());
    }
}
