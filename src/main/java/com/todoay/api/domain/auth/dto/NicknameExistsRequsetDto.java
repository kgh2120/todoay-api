package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.ValidationNickname;
import lombok.Data;

@Data
public class NicknameExistsRequsetDto {

    @ValidationNickname
    private String nickname;
}
