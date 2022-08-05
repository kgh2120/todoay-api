package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.*;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.domain.auth.service.RefreshTokenService;
import com.todoay.api.domain.profile.service.ProfileService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ProfileService profileService;
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "회원가입을 한다.",
            description = "이메일,패스워드,닉네임을 입력하여 회원가입 진행. 각 항목별로 양식을 지키지 않을 경우 오류 발생.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "올바른 이메일/패스워드/닉네임 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "허용되지 않은 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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
                    @ApiResponse(responseCode = "400", description = "올바른 이메일,패스워드 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "403",description = "로그인 할 수 없는 계정으로 로그인 시도", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "일치하는 회원정보가 없음.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        LoginResponseDto tokens = authService.login(loginRequestDto);
        refreshTokenService.login(loginRequestDto,tokens.getRefreshToken()); // refreshToken 저장
        return ResponseEntity.status(201).body(tokens);
    }

    @Operation(
            summary = "계정의 비밀번호를 변경한다",
            description ="토큰을 통해 얻은 email에 해당하는 계정의 비밀번호를 수정한다. 토큰이 존재하지 않거나, 입력한 비밀번호가 양식을 지키지 않는다면 오류가 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "올바른 비밀번호 양식을 입력하지 않음",content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))) ,
                    @ApiResponse(responseCode = "401", description = "AccessToken 만료 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403",description = "허락되지 않은 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Origin password가 저장된 값과 일치하지 않을 때 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated AuthUpdatePasswordRequestDto dto) {

        authService.updateAuthPassword(dto);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "계정을 삭제한다",
            description = "토큰을 통해 얻은 email에 해당하는 계정의 상태를 삭제함으로 변경한다. 토큰에 관련되어 문제가 생길 경우 오류를 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "401", description = "AccessToken 만료 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403",description = "허락되지 않은 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/my")
    public ResponseEntity<Void> deleteAccount() {

        authService.deleteAuth();
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "닉네임 중복 여부를 응답한다.",
            description = "전달받은 nickname이 이미 저장된 닉네임인지 여부를 응답한다. 입력한 닉네임이 유효성 검사를 통과하지 못하면 오류를 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "1. false : 닉네임이 존재하지 않음 \t\n2. true : 닉네임이 존재함"),
                    @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/nickname-exists")
    public ResponseEntity<NicknameExistsResponseDto> nicknameExists(@Validated NicknameExistsRequsetDto dto) {
        boolean exists = profileService.nicknameExists(dto.getNickname());
        return ResponseEntity.ok(NicknameExistsResponseDto.builder().nicknameExists(exists).build());
    }

    @Operation(
            summary = "이메일 중복 여부를 응답한다.",
            description = "전달받은 email이 이미 저장된 닉네임인지 여부를 응답한다. 입력한 email이 유효성 검사를 통과하지 못하면 오류를 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "1. false : 이메일이 존재하지 않음 \t\n2. true : 이메일이 존재함"),
                    @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/email-exists")
    public ResponseEntity<EmailExistsResponseDto> emailExists(@Validated AuthEmailExistsReqeustDto dto) {
        boolean emailExists = authService.emailExists(dto.getEmail());
        return ResponseEntity.ok(EmailExistsResponseDto.builder().emailExists(emailExists).build());
    }




    @Operation(
            summary = "토큰 재발급",
            description = "accessToken을 재발급 받기 위해 refreshToken을 전달한다. refreshToken에 대한 유효성 검사 이후 이를 통과하면 새로운 토큰을 발급해준다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "새로운 토큰을 발급한다.", content = @Content(schema = @Schema(implementation = RefreshResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "RefreshToken이 만료됨", content = @Content(schema = @Schema(implementation = ErrorResponse.class)) ),
                    @ApiResponse(responseCode = "403",description = "허락되지 않은 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "전달받은 refreshToken이 존재하지 않음.",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }

    )
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refreshAccessToken(@RequestBody @Validated RefreshRequestDto dto) {
        RefreshResponseDto responseDto = refreshTokenService.refreshTokens(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }



}
