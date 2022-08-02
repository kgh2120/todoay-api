package com.todoay.api.domain.auth.dto;


import com.todoay.api.global.customValidation.annotation.CPassword;
import lombok.Data;

@Data
public class AuthUpdatePasswordRequestDto {

    @CPassword
    private String originPassword;
    @CPassword
    private String modifiedPassword;
}
