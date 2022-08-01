package com.todoay.api.domain.profile.entity;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String imgUrl;

    private String introMsg;

    // Auth table의 PK 참조 (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_Id")
    private Auth auth;

    @Builder  //이게 있으면 쉽게 객체 생성이 가능하다
    public Profile(String nickname, String imgUrl, String introMsg) {
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.introMsg = introMsg;
    }

    public void updateProfile(ProfileUpdateReqeustDto dto) {
        this.nickname = dto.getNickname();
        this.imgUrl = dto.getImageUrl();
        this.introMsg = dto.getIntroMsg();
    }

    public void delete() {
        this.nickname = UUID.randomUUID().toString();
        this.imgUrl = null;
        this.introMsg = null;
    }


}
