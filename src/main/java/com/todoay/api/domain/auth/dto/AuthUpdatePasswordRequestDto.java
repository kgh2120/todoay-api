package com.todoay.api.domain.auth.dto;


import com.todoay.api.global.customValidation.annotation.ValidationPassword;
import lombok.Data;

@Data
public class AuthUpdatePasswordRequestDto {

    @ValidationPassword
    private String originPassword;
    @ValidationPassword
    private String modifiedPassword;
}
