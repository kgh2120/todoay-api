package com.todoay.api.domain.auth.dto;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.entity.Profile;
import com.todoay.api.global.customValidation.annotation.ValidationEmail;
import com.todoay.api.global.customValidation.annotation.ValidationNickname;
import com.todoay.api.global.customValidation.annotation.ValidationPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSaveDto {
    @Schema(required = true)
    @ValidationEmail
    private String email;

    @ValidationPassword
    private String password;

    @ValidationNickname
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

