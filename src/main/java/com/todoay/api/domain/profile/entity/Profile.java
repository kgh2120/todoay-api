package com.todoay.api.domain.profile.entity;
import com.todoay.api.domain.auth.entity.Auth;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "img_url")
    private String img_url;

    @Column(name = "intro_msg")
    private String intro_msg;

    // Auth table의 PK 참조 (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private Auth auth;
}
