package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.RefreshRequestDto;
import com.todoay.api.domain.auth.dto.RefreshResponseDto;
import com.todoay.api.domain.auth.entity.RefreshToken;
import com.todoay.api.domain.auth.exception.RefreshTokenExpiredException;
import com.todoay.api.domain.auth.exception.RefreshTokenNotFoundException;
import com.todoay.api.domain.auth.repository.RefreshTokenRepository;
import com.todoay.api.global.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service @Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void login(LoginRequestDto dto, String refreshToken) {
        String email = dto.getEmail();

        refreshTokenRepository.findBySubjectEmail(email)
                .ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.save(new RefreshToken(refreshToken, email));
    }

    @Override
    public RefreshResponseDto refreshTokens(RefreshRequestDto dto) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);

        // refreshToken validation
        try {
            jwtProvider.validateToken(refreshToken.getToken());
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException();
        }
        String accessToken = jwtProvider.createAccessToken(refreshToken.getSubjectEmail());

        // refresh refreshToken expiration
        String updatedRefreshToken = jwtProvider.createRefreshToken(refreshToken.getSubjectEmail());
        refreshToken.updateRefreshToken(updatedRefreshToken);

        return RefreshResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(updatedRefreshToken)
                .build();
    }
}
