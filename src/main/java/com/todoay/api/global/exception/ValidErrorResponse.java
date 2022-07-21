package com.todoay.api.global.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;

import static com.todoay.api.global.exception.GlobalErrorCode.ARGUMENT_FORMAT_INVALID;

@Getter
@SuperBuilder
public class ValidErrorResponse extends ErrorResponse{
    private final List<ValidDetail> details;

    public static ResponseEntity<ValidErrorResponse> toResponseEntity(List<ValidDetail> detalis, String path) {

        return ResponseEntity
                .status(ARGUMENT_FORMAT_INVALID.getHttpStatus())
                .body(ValidErrorResponse.builder()
                        .status(ARGUMENT_FORMAT_INVALID.getHttpStatus().value())
                        .error(ARGUMENT_FORMAT_INVALID.getHttpStatus().name())
                        .code(ARGUMENT_FORMAT_INVALID.name())
                        .message(ARGUMENT_FORMAT_INVALID.getDetailMessage())
                        .path(path)
                        .details(detalis)
                        .build()
                );
    }

    public static ResponseEntity<ValidErrorResponse> toResponseEntity(BindException ex, String path) {
        List<ValidDetail> details = ex.getBindingResult().getFieldErrors().stream().map(e ->
                ValidDetail.builder()
                        .code(e.getCode())
                        .field(e.getField())
                        .rejectedValue(e.getRejectedValue())
                        .message(e.getDefaultMessage())
                        .build()
        ).collect(Collectors.toList());
        return toResponseEntity(details, path);
    }
}
