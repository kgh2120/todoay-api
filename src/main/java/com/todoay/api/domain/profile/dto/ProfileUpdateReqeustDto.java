package com.todoay.api.domain.profile.dto;

import com.todoay.api.global.customValidation.annotation.CNickname;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProfileUpdateReqeustDto {

    // TODO nickname 설정 제약조건 회원가입과 통일시키기.

    @CNickname
    private String nickname;

    private String introMsg;

    private String imageUrl;
}
