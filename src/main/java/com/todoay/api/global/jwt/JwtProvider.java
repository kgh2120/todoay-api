package com.todoay.api.global.jwt;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.global.exception.JwtHeaderNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.key}")
    private String secretKey;
    private final long EMAIL_TOKEN_EXPIRATION = 1000 * 60 * 5L;
    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24L;
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 30L;

    private final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @PostConstruct // init() 메소드
    protected void init() {  // secretKey를 Base64형식으로 인코딩함. 인코딩 전후 확인 로깅
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        System.out.println(secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }


    private String createToken(long expirationTime, String email) {
        // 토큰 생성 시작
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String createEmailToken(String email) {
        return createToken(EMAIL_TOKEN_EXPIRATION, email);
    }

    public String createAccessToken(String email) {
        return createToken(ACCESS_TOKEN_EXPIRATION, email);
    }

    public String createRefreshToken(String email) {
        return createToken(REFRESH_TOKEN_EXPIRATION, email);
    }

    public String getUserEmail(String token) {
        // Jwts.parser()로 secretKey를 설정하고 claim을 추출해서 토큰 생성할 때 넣었던 sub 값 추출
        try {
            LOGGER.info("[getUserEmail] 토큰 기반 회원 구별 정보 추출");
            String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                    .getSubject();
            LOGGER.info("[getUserEmail] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
            return info;
        } catch (IllegalArgumentException e) {
            throw new JwtHeaderNotFoundException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    public String getLoginId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Auth auth = (Auth) authentication.getPrincipal();
        LOGGER.info("auth = {}", auth);
        return auth.getEmail();
    }

    public Claims validateToken(String token) {
        try {
            LOGGER.info("[validateToken] 토큰 유효 체크 시작");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody();
        } catch (IllegalArgumentException e) {
            throw new JwtHeaderNotFoundException();
        }
    }
}
