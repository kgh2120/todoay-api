package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.*;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.auth.utility.MailHandler;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailVerificationServiceImpl implements MailVerificationService {
    private final AuthRepository authRepository;

    private final JavaMailSender mailSender;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;

    @Override
    public String sendVerificationMail(AuthSendEmailRequestDto authSendEmailRequestDto) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(authSendEmailRequestDto.getEmail());
            mailHandler.setSubject("[TODOAY] 이메일 인증을 완료해주세요.");
            String emailToken = jwtProvider.createEmailToken(authSendEmailRequestDto.getEmail());
            String sb = "<a href='" +
                    "http://" + "localhost:8080/auth/email-verification?emailToken=" + emailToken +
                    "')>링크를 클릭하여 인증을 완료해주세요</a>";
            mailHandler.setText(sb, true);
            mailHandler.send();
            return emailToken;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public void verifyEmailOnSignUp(AuthVerifyEmailTokenOnSingUpDto authVerifyEmailTokenOnSingUpDto) {
//         io.jsonwebtoken.UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
//         io.jsonwebtoken.MalformedJwtException – if the claimsJws string is not a valid JWS
//         io.jsonwebtoken.SignatureException – if the claimsJws JWS signature validation fails
//         io.jsonwebtoken.ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
//         IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
        Auth auth = this.verifyEmailToken(authVerifyEmailTokenOnSingUpDto.getEmailToken());
        auth.completeEmailVerification();
    }

    @Override
    @Transactional
    public String verifyEmailOnUpdatePassword(AuthVerifyEmailTokenOnUpdatePassword dto) {
        Auth auth = this.verifyEmailToken(dto.getEmailToken());
        String newPassword = UUID.randomUUID().toString().substring(0, 10);
        String encoded = encoder.encode(newPassword);
        auth.updatePassword(encoded);
        return newPassword;
    }

    private Auth verifyEmailToken(String emailToken) {
        return authRepository.findByEmail(jwtProvider.validateToken(emailToken).getSubject()).orElseThrow(EmailNotFoundException::new);
    }

    @Override
    public CheckEmailVerifiedResponseDto checkEmailVerified(String email) {
        Auth auth = authRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        boolean emailVerified = auth.getEmailVerifiedAt() == null ? false : true;
        return CheckEmailVerifiedResponseDto.builder().emailVerified(emailVerified).build();
    }

    @Override
    public String sendUpdatePasswordMail(AuthSendUpdatePasswordMailRequestDto dto) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(dto.getEmail());
            mailHandler.setSubject("[TODOAY] 비밀번호 찾기");
            String emailToken = jwtProvider.createEmailToken(dto.getEmail());
            String sb = "<a href='" +
                    "http://" + "localhost:8080/auth/email-verification/update-password?emailToken=" + emailToken +
                    "')>링크를 클릭하면 비밀번호를 임시 비밀번호로 변경합니다.</a>";
            mailHandler.setText(sb, true);
            mailHandler.send();
            return emailToken;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
