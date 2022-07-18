package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import com.todoay.api.domain.auth.service.MainVerificationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    private final MainVerificationService mainVerificationService;

    @ApiOperation(value = "이메일로 인증 메일은 보낸다.", notes = "입력한 이메일로 인증 메일은 보낸다. 입력한 이메일이 이메일 양식을 지키지 않을 경우 오류 발생.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code=204, message = "성공"),
            @ApiResponse(code = 400, message = "올바른 이메일 양식을 입력하지 않음."),
    })
    @GetMapping("/auth/mail")
    public ResponseEntity<Void> sendVerificationMail(@Valid EmailDto emailDto) {
        mainVerificationService.sendVerificationMail(emailDto);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/auth/email-verification")
    public ResponseEntity<Void> verifyEmailToken(@RequestBody EmailTokenDto emailTokenDto) {
        mainVerificationService.verifyEmailToken(emailTokenDto);
        return ResponseEntity.noContent().build();
    }
}
