package com.todoay.api.domain.auth.dto;

import com.todoay.api.global.customValidation.annotation.CNickname;
import lombok.Data;

@Data
public class NicknameExistsRequsetDto {

    @CNickname
    private String nickname;
}
