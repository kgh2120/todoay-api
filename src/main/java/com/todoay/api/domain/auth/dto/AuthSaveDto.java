package com.todoay.api.domain.auth.dto;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.profile.entity.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthSaveDto {
    @Schema(required = true)
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\^&()-])(?=.*[a-zA-Z]).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자로 이루어진 8~20자로 입력해야합니다.")
    private String password;

    @NotBlank
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

