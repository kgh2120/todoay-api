package com.todoay.api.domain.auth.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PasswordTest {
    
    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    void passwordMatchTest() {
        String pwd = "qwer1234!";
        boolean matches = encoder.matches(pwd, "$2a$10$daPL3IdYP3Lc0TTGVRCdD.Q8dmD5u4boVvXwOtiHRpm0EFCBg2xk.");
        assertThat(matches).isTrue();
    }
}
