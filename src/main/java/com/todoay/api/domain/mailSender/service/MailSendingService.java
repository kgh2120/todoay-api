package com.todoay.api.domain.mailSender.service;

import com.todoay.api.domain.mailSender.dto.MailDto;
import com.todoay.api.domain.mailSender.utility.MailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class MailSendingService {
    private final JavaMailSender mailSender;

    public void sendEmail(String email) {
        try {
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(email);
            mailHandler.setSubject("[TODOAY] 이메일 인증을 완료해주세요.");
            String emailToken = "1234";
            // emailToken = TokenManager.createEmailToken(email, 60*5);
            mailHandler.setText("<h1>TODOAY</h1></br><p>CODE: " + emailToken +"</p></br>", true);
            mailHandler.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
