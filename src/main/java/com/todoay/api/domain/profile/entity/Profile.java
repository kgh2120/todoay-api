package com.todoay.api.domain.profile.entity;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.controller.Dto.ProfileSaveDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Profile implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    private String imgUrl;

    private String introMsg;

    // Auth table의 PK 참조 (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private Auth auth;

    public static Profile saveProfile(ProfileSaveDto profileSaveDto) {
        // profileEntity타입의 객체를 보내기 위해 profileEntity라는 객체 선언
       Profile profile = new Profile();

        // profileEntity 객체에 profileSaveDto 객체 안에 담긴 각 요소를 담아줌.
        profile.setNickname(profileSaveDto.getNickname());
        profile.setImgUrl(profileSaveDto.getImgUrl());
        profile.setIntroMsg(profileSaveDto.getIntroMsg());

        // 변환이 완료된 profileEntity 객체를 넘겨줌
        return profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
