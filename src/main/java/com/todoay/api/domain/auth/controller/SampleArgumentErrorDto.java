package com.todoay.api.domain.auth.controller;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SampleArgumentErrorDto {

    @Email @NotBlank @NotNull(message = "이메일을 입력하셔야 합니다.")
    private String email;

    @NotNull(message = "이름을 입력하셔야 합니다.") @Size(min = 2, message = "이름은 2글자 이상 입력하셔야 합니다.")
    private String name;

    @NotNull(message = "비밀번호를 입력하셔야 합니다.") @Size(min = 8, message = "비밀번호는 8글자 이상 입력하셔야 합니다.")
    private String password;

}
