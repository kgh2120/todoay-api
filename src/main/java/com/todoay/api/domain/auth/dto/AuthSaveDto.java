package com.todoay.api.domain.auth.dto;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.entity.Profile;
import com.todoay.api.global.customValidation.annotation.CEmail;
import com.todoay.api.global.customValidation.annotation.CNickname;
import com.todoay.api.global.customValidation.annotation.CPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSaveDto {
    @Schema(required = true)
    @CEmail
    private String email;

    @CPassword
    private String password;

    @CNickname
    private String nickname;

    public Auth toAuthEntity() {
        Auth authEntity = Auth.builder()
                .email(email)
                .password(password).build();
        authEntity.associateWithProfile(toProfileEntity());
        return authEntity;
    }

    public Profile toProfileEntity() {
        return Profile.builder()
                .nickname(nickname).build();
    }
}

