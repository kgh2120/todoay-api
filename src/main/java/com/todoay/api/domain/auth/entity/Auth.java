package com.todoay.api.domain.auth.entity;

import com.todoay.api.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class Auth implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY, TABLE, SEQUENCE
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime emailVerifiedAt;  // null일 경우 인증되지 않음. null이 아닐 경우는 해당 시간에 인증되었음을 의미.


    @OneToOne(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;


    @Builder  //이게 있으면 쉽게 객체 생성이 가능하다
    public Auth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void associateWithProfile(Profile profile) {
        this.profile = profile;
        profile.setAuth(this);
    }

    public void updatePassword(String password) {
        this.password = password;
    }


    @Override // 계정이 가지고 있는 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override // 계정의 비밀번호 리턴
    public String getPassword() {
        return password;
    }

    @Override // 계정의 이름 리턴
    public String getUsername() {
        return email;
    }

    @Override // 계정이 만료되었는지 리턴. True: 만료되지 않음
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정이 잠겨있는지 리턴. True: 잠기지 않음
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호가 만료되었는지 리턴. True: 만료되지 않음
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 활성화 되어있는지 리턴. True: 활성화 상태
    public boolean isEnabled() {
        return true;
    }
}
