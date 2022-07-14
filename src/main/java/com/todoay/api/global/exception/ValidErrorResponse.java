package com.todoay.api.global.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
    public void addDetail(ValidDetail detail) {
        this.details.add(detail);
    }
}
