package com.todoay.api.domain.auth.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthUpdatePasswordReqeustDto {

    @NotNull
    private String password; // TODO 제약조건 추가 회원가입하고 동일하게 만들어야 함
}
