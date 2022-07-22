package com.todoay.api.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(required = true)  // swagger에 설명을 추가해주는
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

}
