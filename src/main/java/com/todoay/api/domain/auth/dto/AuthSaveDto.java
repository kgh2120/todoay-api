package com.todoay.api.domain.auth.dto;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSaveDto {
    @Schema(required = true)
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank
    private String nickname;

    public Auth toAuthEntity() {
        Auth authEntity = Auth.builder()
                .email(email)
                .password(password).build();
        authEntity.setProfile(toProfileEntity());
        return authEntity;
    }

    public Profile toProfileEntity() {
        return Profile.builder()
                .nickname(nickname).build();
    }
}

