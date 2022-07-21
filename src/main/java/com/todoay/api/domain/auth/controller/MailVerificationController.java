package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import com.todoay.api.domain.auth.service.MailVerificationService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MailVerificationController {
    private final MailVerificationService mailVerificationService;

    @Operation(
            summary = "이메일로 인증 메일을 보낸다.",
            description = "입력한 이메일로 인증 메일을 보낸다. 입력한 이메일이 이메일 양식을 지키지 않을 경우 오류 발생.",
            responses = {
                @ApiResponse(responseCode = "204", description = "성공"),
                @ApiResponse(responseCode = "400", description = "올바른 이메일 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class)))
            }
    )
    @GetMapping("/auth/mail")
    public ResponseEntity<Void> sendVerificationMail(@Valid EmailDto emailDto) {
        mailVerificationService.sendVerificationMail(emailDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/email-verification")
    @Operation(
            summary = "이메일 인증 토큰이 유효한지 검사한다.",
            description = "토큰은 request body로 받는다. 토큰의 형식이 잘못되었거나 만료되었을 경우 401을 응답한다.",
            responses = {
                @ApiResponse(responseCode = "204", description = "토큰이 유효함"),
                @ApiResponse(responseCode = "401", description = "JWT_EXPIRED, JWT_NOT_VERIFIED, JWT_NOT_VERIFIED, JWT_MALFORMED, JWT_UNSUPPORTED", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    public ResponseEntity<Void> verifyEmailToken(@RequestBody @Valid EmailTokenDto emailTokenDto) {
        mailVerificationService.verifyEmailToken(emailTokenDto);
        return ResponseEntity.noContent().build();
    }
}