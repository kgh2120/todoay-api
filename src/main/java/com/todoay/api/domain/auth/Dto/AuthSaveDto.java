package com.todoay.api.domain.auth.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSaveDto {
    private String email;
    private String password;
}
