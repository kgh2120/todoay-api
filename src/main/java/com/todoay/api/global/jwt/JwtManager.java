package com.todoay.api.global.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@PropertySource("classpath:secret.properties")
@Component
public class JwtManager {
    @Value("${jwt.key}")
    private String key;
    private final long EMAIL_TOKEN_EXPIRATION = 1000 * 60 * 5;

    public String createEmailToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setExpiration(getDateAfter(EMAIL_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private Date getDateAfter(long time) {
        Date date = new Date();
        date.setTime(date.getTime() + time);
        return date;
    }

    public Claims verify(String emailToken) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(emailToken).getBody();
    }
}
