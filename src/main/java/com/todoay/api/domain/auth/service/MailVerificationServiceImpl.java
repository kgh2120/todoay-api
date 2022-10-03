package com.todoay.api.domain.auth.service;

import com.todoay.api.domain.auth.dto.*;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.auth.utility.MailHandler;
import com.todoay.api.domain.auth.utility.MailTextCreator;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final CategoryRepository categoryRepository;

    private final JavaMailSender mailSender;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;

    @Value("${url.base}")
    private String baseUrl;

    @Override
    public String sendVerificationMail(AuthSendEmailRequestDto authSendEmailRequestDto) {
        try {

            return sendMail(authSendEmailRequestDto.getEmail(),
                    "[TODOAY] 이메일 인증을 완료해주세요.",
                    "/auth/email-verification?emailToken="
            ,"링크를 클릭하여 인증을 완료해주세요.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private enum DefaultCategory {
        NAME("일반"), COLOR("#ff333d79"), ORDER_INDEX(0)
        ;
        private Object attributeValue;
        DefaultCategory(Object attributeValue) {
            this.attributeValue = attributeValue;
        }
        public static Category getDefaultCategory(Auth auth) {
            return Category.builder()
                    .name((String) NAME.attributeValue)
                    .color((String) COLOR.attributeValue)
                    .auth(auth)
                    .orderIndex((int) ORDER_INDEX.attributeValue)
                    .build();
        }
    }

    @Override
    @Transactional
    public void verifyEmailOnSignUp(AuthVerifyEmailTokenOnSignUpRequestDto authVerifyEmailTokenOnSignUpRequestDto) {
        Auth auth = this.verifyEmailToken(authVerifyEmailTokenOnSignUpRequestDto.getEmailToken());
        if (auth.getEmailVerifiedAt() == null) {
            auth.completeEmailVerification();
            categoryRepository.save(DefaultCategory.getDefaultCategory(auth));
        }
    }

    @Override
    @Transactional
    public String verifyEmailOnUpdatePassword(AuthVerifyEmailTokenOnUpdatePasswordRequestDto dto) {
        Auth auth = this.verifyEmailToken(dto.getEmailToken());
        String newPassword = UUID.randomUUID().toString().substring(0, 10);
        String encoded = encoder.encode(newPassword);
        auth.updatePassword(encoded);
        return newPassword;
    }

    private Auth verifyEmailToken(String emailToken) {
        return authRepository.findByEmail(jwtProvider.validateToken(emailToken).getSubject())
                .orElseThrow(EmailNotFoundException::new);
    }

    @Override
    public CheckEmailVerifiedResponseDto checkEmailVerified(String email) {
        Auth auth = authRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        boolean emailVerified = auth.getEmailVerifiedAt() != null;
        return CheckEmailVerifiedResponseDto.builder().emailVerified(emailVerified).build();
    }

    @Override
    public String sendUpdatePasswordMail(AuthSendUpdatePasswordMailRequestDto dto) {
        try {
            return sendMail(dto.getEmail(), "[TODOAY] 비밀번호 찾기",
                    "/auth/email-verification/update-password?emailToken=",
                    "링크를 클릭하면 비밀번호를 임시 비밀번호로 변경합니다."
                    );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String sendMail(String email, String subject, String path, String message) throws MessagingException {
        MailHandler mailHandler = new MailHandler(mailSender);
        mailHandler.setTo(email);
        mailHandler.setSubject(subject);
        String emailToken = jwtProvider.createEmailToken(email);
        String sb = MailTextCreator.createMailText(baseUrl,path
        ,emailToken,message);
        mailHandler.setText(sb, true);
        new Thread(mailHandler::send).start();
        return emailToken;
    }


}
