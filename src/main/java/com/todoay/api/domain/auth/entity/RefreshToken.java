package com.todoay.api.domain.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String subjectEmail;

    public RefreshToken(String token, String subjectEmail) {
        this.token = token;
        this.subjectEmail = subjectEmail;
    }

    public void updateRefreshToken(String refreshToken) {
        this.token = refreshToken;
    }
}
