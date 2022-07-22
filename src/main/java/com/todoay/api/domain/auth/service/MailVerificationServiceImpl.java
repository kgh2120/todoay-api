package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.EmailDto;
import com.todoay.api.domain.auth.dto.EmailTokenDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.auth.utility.MailHandler;
import com.todoay.api.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailVerificationServiceImpl implements MailVerificationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;
    private final AuthRepository authRepository;

    @Override
    public void sendVerificationMail(EmailDto emailDto) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(emailDto.getEmail());
            mailHandler.setSubject("[TODOAY] 이메일 인증을 완료해주세요.");
            String emailToken = jwtTokenProvider.createEmailToken(emailDto.getEmail());
            StringBuilder sb = new StringBuilder();
            sb.append("<a href='")
                    .append("http://").append("localhost:8080/auth/email-verification?emailToken=").append(emailToken)
                    .append("')>링크를 클릭하여 인증을 완료해주세요</a>");
            mailHandler.setText(sb.toString(), true);
            mailHandler.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyEmail(EmailTokenDto emailTokenDto) {
//         io.jsonwebtoken.UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
//         io.jsonwebtoken.MalformedJwtException – if the claimsJws string is not a valid JWS
//         io.jsonwebtoken.SignatureException – if the claimsJws JWS signature validation fails
//         io.jsonwebtoken.ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
//         IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
        String email = jwtTokenProvider.validateToken(emailTokenDto.getEmailToken()).get("email", String.class);
        authRepository.findByEmail(email).orElseThrow(); // pull 받은 후 작업 예정

    }
}
