package com.todoay.api.global.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

import static com.todoay.api.domain.profile.exception.ProfileErrorCode.SIZE_LIMIT_EXCEEDED;
import static com.todoay.api.global.exception.GlobalErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleAbstractApiException(AbstractApiException e, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(e, request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidErrorResponse> handleBindException(BindException ex, HttpServletRequest request) {
        return ValidErrorResponse.toResponseEntity(ex, request.getRequestURI());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(SQL_INTEGRITY_CONSTRAINT_VIOLATION, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(REQUEST_PARAM_CONSTRAINT_VIOLATION, request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(REQUEST_PARAM_MISSING, request.getRequestURI());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(REQUEST_PARAM_TYPE_MISMATCH, request.getRequestURI());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(JWT_EXPIRED, request.getRequestURI());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(JWT_NOT_VERIFIED, request.getRequestURI());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(JWT_MALFORMED, request.getRequestURI());
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(JWT_UNSUPPORTED, request.getRequestURI());
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(JSON_PARSE_ERROR, request.getRequestURI());
    }
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleSizeLimitExceededException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(SIZE_LIMIT_EXCEEDED, request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(HttpServletRequest request) {
        return ErrorResponse.toResponseEntity(MISSING_REQUEST_PART, request.getRequestURI());
    }

}
