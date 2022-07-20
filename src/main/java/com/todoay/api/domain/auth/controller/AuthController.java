package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${header.access-token}")
    private String accessToken;
    @Value("${header.email-token}")
    private String emailToken;

    @PostMapping("save")
    public AuthSaveDto signup(@RequestBody AuthSaveDto authSaveDto) {
        authService.save(authSaveDto);
        // save까지 authService interface에 구현?
        return authSaveDto;
    }

    @PatchMapping("/password")
    public ResponseEntity changePassword(HttpServletRequest request, @RequestBody @Validated AuthUpdatePasswordReqeustDto dto) {

        // 400 실패  password 유효성 검사 실패 경우인듯.
        // 401 실패 아마 토큰이 없는 경우인듯
        String token = getJwtEmailOrAccessTokenByHeader(request);
        authService.updateAuthPassword(token,dto);

        return ResponseEntity.status(200).build();
    }

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
