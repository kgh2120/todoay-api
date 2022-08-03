package com.todoay.api.domain.profile.dto;

import com.todoay.api.global.customValidation.annotation.ValidationNickname;
import lombok.Data;

@Data
public class ProfileUpdateReqeustDto {

    // TODO nickname 설정 제약조건 회원가입과 통일시키기.

    @ValidationNickname
    private String nickname;

    private String introMsg;

    private String imageUrl;
}
