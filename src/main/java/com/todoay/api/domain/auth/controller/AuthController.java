package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.LoginResponseDto;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

//    private final AuthServiceImpl authServiceImpl;
    private final AuthService authService;

    @Operation(
            summary = "회원가입을 한다.",
            description = "이메일,패스워드,닉네임을 입력하여 회원가입 진행. 각 항목별로 양식을 지키지 않을 경우 오류 발생.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "올바른 이메일/패스워드/닉네임 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class)))
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Validated AuthSaveDto authSaveDto) {  // validated하고 설정하면 그 중에 몇개만 골라서 검사 해줌. valid는 다 함
        authService.save(authSaveDto);
        // save까지 authService interface에 구현?
        return ResponseEntity.noContent().build();
    }

    // login 할 때는 jwt로 반환하기로
    @Operation(
            summary = "로그인을 한다.",
            description = "가입된 이메일과 패스워드로 로그인을 진행. 입력한 이메일과 패스워드가 가입된 이메일, 패스워드와 다르거나 없는 경우 오류 발생.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "일치하는 회원정보가 없음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class)))
            }
    )
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        LoginResponseDto login = authService.login(loginRequestDto);
        return ResponseEntity.status(201).body(login);
    }
}
