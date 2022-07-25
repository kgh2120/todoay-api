package com.todoay.api.domain.profile.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProfileUpdateReqeustDto {

    // TODO nickname 설정 제약조건 회원가입과 통일시키기.

    @NotNull @Size // 회원가입하고 제약조건 통일시켜야 함.
    private String nickname;

    private String introMsg;

    private String imageUrl;
}
