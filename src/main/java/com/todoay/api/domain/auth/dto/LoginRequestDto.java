package com.todoay.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(required = true)  // swagger에 설명을 추가해주는
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\^&()-])(?=.*[a-zA-Z]).{8,20}$", message = "비밀번호는 영문, 숫자, 특수문자로 이루어진 8~20자로 입력해야합니다.")
    private String password;

}
