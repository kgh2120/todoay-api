package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.LoginResponseDto;
import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Validated AuthSaveDto authSaveDto) {  // validated하고 설정하면 그 중에 몇개만 골라서 검사 해줌. valid는 다 함
    @Value("${header.access-token}")
    private String accessToken;
    @Value("${header.email-token}")
    private String emailToken;

    @PostMapping("save")
    public AuthSaveDto signup(@RequestBody AuthSaveDto authSaveDto) {
        authService.save(authSaveDto);
        // save까지 authService interface에 구현?
        return ResponseEntity.noContent().build();
    }

    // login 할 때는 jwt로 반환하기로
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        LoginResponseDto login = authService.login(loginRequestDto);
        return ResponseEntity.status(201).body(login);
    }

    @Operation(
            summary = "계정의 비밀번호를 변경한다",
            description ="토큰을 통해 얻은 email에 해당하는 계정의 비밀번호를 수정한다. 토큰이 존재하지 않거나, 입력한 비밀번호가 양식을 지키지 않는다면 오류가 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "올바른 비밀번호 양식을 입력하지 않음",content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))) ,
                    @ApiResponse(responseCode = "401", description = "JWT 토큰 에러 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/password")
    public ResponseEntity changePassword(HttpServletRequest request, @RequestBody @Validated AuthUpdatePasswordReqeustDto dto) {

        // 400 실패  password 유효성 검사 실패 경우인듯.
        // 401 실패 아마 토큰이 없는 경우인듯
        String token = getJwtEmailOrAccessTokenByHeader(request);
        authService.updateAuthPassword(token,dto);

        return ResponseEntity.status(200).build();
    }

    @Operation(
            summary = "계정을 삭제한다",
            description = "토큰을 통해 얻은 email에 해당하는 계정의 상태를 삭제함으로 변경한다. 토큰에 관련되어 문제가 생길 경우 오류를 발생한다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "401", description = "JWT 토큰 에러" ,content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @DeleteMapping("/my")
    public ResponseEntity deleteAccount(HttpServletRequest request) {

        String jwtAccessToken = getJwtAccessToken(request);
        authService.deleteAuth(jwtAccessToken);

        return ResponseEntity.status(204).build();
    }

    private String getJwtEmailOrAccessTokenByHeader(HttpServletRequest request) { // jwt 관련 클래스가 있다면 거기로 이전하는 것이 좋아보임.
        String aToken = getJwtToken(request,accessToken);
        String eToken = getJwtToken(request,emailToken);

        String token = aToken != null ? aToken : eToken;
        isExistToken(token);

        return token;
    }

    private String getJwtAccessToken(HttpServletRequest request) {
        String jwtToken = getJwtToken(request, accessToken);
        isExistToken(jwtToken);
        return jwtToken;
    }

    private void isExistToken(String token) {
        if (token == null) { // email, access 둘다 토큰이 존재하지 않음.
            throw new IllegalArgumentException(); // TODO token이 없는데 접근하는 exception 추가
        }
    }


    private String getJwtToken(HttpServletRequest request, String header) {
        return request.getHeader(header);
    }
}
