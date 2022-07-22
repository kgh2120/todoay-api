package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSendEmailRequestDto;
import com.todoay.api.domain.auth.dto.AuthVerifyEmailTokenOnSingUpDto;
import com.todoay.api.domain.auth.service.MailVerificationService;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.JwtException;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
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
    @GetMapping("/send-mail")
    public ResponseEntity<Void> sendVerificationMail(@Valid AuthSendEmailRequestDto authSendEmailRequestDto) {
        mailVerificationService.sendVerificationMail(authSendEmailRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email-verification")
    @Operation(
            summary = "이메일 인증 토큰이 유효한지 검사하고 HTML을 반환한다.",
            description = "토큰은 path parameter 로 받는다. 토큰의 형식이 잘못되었거나 만료되었을 경우 401을 응답한다. 이메일로 제공되는 인증 링크로만 사용된다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "토큰이 유효함"),
                    @ApiResponse(responseCode = "401", description = "JWT_EXPIRED, JWT_NOT_VERIFIED, JWT_NOT_VERIFIED, JWT_MALFORMED, JWT_UNSUPPORTED", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    public ModelAndView verifyEmailTokenOnSignUp(@Valid AuthVerifyEmailTokenOnSingUpDto authVerifyEmailTOkenOnSingUpDto) {
        ModelAndView modelAndView = new ModelAndView("email-verification");
        try {
            mailVerificationService.verifyEmail(authVerifyEmailTOkenOnSingUpDto);
        }  catch (JwtException | EmailNotFoundException e) {
            modelAndView.addObject("exception", e.getClass().getSimpleName());
        }
        return modelAndView;
    }
}
