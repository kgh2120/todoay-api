package com.todoay.api.domain.auth.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AuthUpdatePasswordReqeustDto {

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\^&()-])(?=.*[a-zA-Z]).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자로 이루어진 8~20자로 입력해야합니다.")
    private String originPassword;
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\^&()-])(?=.*[a-zA-Z]).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자로 이루어진 8~20자로 입력해야합니다.")
    private String modifiedPassword;
}
