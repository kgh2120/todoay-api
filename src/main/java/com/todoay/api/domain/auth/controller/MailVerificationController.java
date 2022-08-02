package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.*;
import com.todoay.api.domain.auth.service.MailVerificationService;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ResponseEntity<Void> sendVerificationMail(@Validated AuthSendEmailRequestDto authSendEmailRequestDto) {
        mailVerificationService.sendVerificationMail(authSendEmailRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email-verification")
    @Operation(
            summary = "이메일 인증 토큰이 유효한지 검사하고 HTML을 반환한다.",
            description = "토큰은 path parameter 로 받는다. 토큰의 형식이 잘못되었거나 만료되었을 경우 401을 응답한다. 이메일로 제공되는 인증 링크로만 사용된다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰인증 결과를 HTML로 반환"),
            }
    )
    public ModelAndView verifyEmailTokenOnSignUp(AuthVerifyEmailTokenOnSingUpRequestDto authVerifyEmailTokenOnSingUpRequestDto) {
        String emailToken = authVerifyEmailTokenOnSingUpRequestDto.getEmailToken();
        ModelAndView modelAndView = new ModelAndView("email-verification");

        if (emailToken == null || emailToken.isBlank()) {
            return modelAndView.addObject("exception", BindException.class.getSimpleName()); // 적절한 exception 으로 바꿔야 함
        }

        try {
            mailVerificationService.verifyEmailOnSignUp(authVerifyEmailTokenOnSingUpRequestDto);
        }  catch (JwtException | EmailNotFoundException e) {
            modelAndView.addObject("exception", e.getClass().getSimpleName());
        }
        return modelAndView;
    }

    @GetMapping("/{email}/email-verified")
    @Operation(
            summary = "path variable로 받은 email의 계정의 이메일 인증 여부를 응답한다.",
            description = "{'emailVerified': boolean}",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CheckEmailVerifiedResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "email에 해당하는 계정이 없는 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<CheckEmailVerifiedResponseDto> checkEmailVerified(@PathVariable String email) {
        CheckEmailVerifiedResponseDto checkEmailVerifiedResponseDto = mailVerificationService.checkEmailVerified(email);
        return ResponseEntity.ok(checkEmailVerifiedResponseDto);
    }

    @GetMapping("/send-mail/update-password")
    @Operation(
            summary = "계정 비밀번호를 임시 비밀번호로 변경하고 알려주는 링크를 담은 메일을 전송한다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "올바른 이메일 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class)))
            }
    )
    public ResponseEntity<Void> sendUpdatePasswordMail(@Validated AuthSendUpdatePasswordMailRequestDto dto) {
        mailVerificationService.sendUpdatePasswordMail(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email-verification/update-password")
    @Operation(
            summary = "계정 비밀번호를 임시 비밀번호로 변경한다. HTML을 응답하여 임시 비밀번호를 알려준다.",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CheckEmailVerifiedResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "email에 해당하는 계정이 없는 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ModelAndView verifyEmailTokenOnUpdatePassword(AuthVerifyEmailTokenOnUpdatePasswordRequestDto dto) {
        String emailToken = dto.getEmailToken();
        ModelAndView modelAndView = new ModelAndView("update-password");
        if (emailToken == null || emailToken.isBlank()) {
            return modelAndView.addObject("exception", BindException.class.getSimpleName()); // 적절한 exception 으로 바꿔야 함
        }
        try{
            String newPassword = mailVerificationService.verifyEmailOnUpdatePassword(dto);
            modelAndView.addObject("password", newPassword);
        }  catch (JwtException | EmailNotFoundException e) {
            modelAndView.addObject("exception", e.getClass().getSimpleName());
        }
        return modelAndView;
    }
}
