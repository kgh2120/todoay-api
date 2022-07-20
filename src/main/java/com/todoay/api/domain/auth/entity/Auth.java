package com.todoay.api.domain.auth.entity;

import com.todoay.api.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Auth implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY, TABLE, SEQUENCE
    private Long id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;


    @Builder  //이게 있으면 쉽게 객체 생성이 가능하다
    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override // 계정이 가지고 있는 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override // 계정의 비밀번호 리턴
    public String getPassword() {
        return null;
    }

    @Override // 계정의 이름 리턴
    public String getUsername() {
        return null;
    }

    @Override // 계정이 만료되었는지 리턴. True: 만료되지 않음
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override // 계정이 잠겨있는지 리턴. True: 잠기지 않음
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override // 비밀번호가 만료되었는지 리턴. True: 만료되지 않음
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override // 계정이 활성화 되어있는지 리턴. True: 활성화 상태
    public boolean isEnabled() {
        return false;
    }
}
