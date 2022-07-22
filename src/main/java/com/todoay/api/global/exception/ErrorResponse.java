package com.todoay.api.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    @Schema(example = "401")
    private final int status;
    @Schema(example = "UNAUTHORIZED")
    private final String error;
    @Schema(example = "JWT_EXPIRED")
    private final String code;
    @Schema(example = "이메일 토큰이 만료되었습니다.")
    private final String message;
    @Schema(example = "/auth/email-verification")
    private final String path;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String path) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetailMessage())
                        .path(path)
                        .build()
                );
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ",\n status=" + status +
                ",\n error='" + error + '\'' +
                ",\n code='" + code + '\'' +
                ",\n message='" + message + '\'' +
                ",\n path='" + path + '\'' +
                '}';
    }
}