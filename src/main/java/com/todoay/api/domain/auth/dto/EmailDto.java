package com.todoay.api.domain.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class EmailDto {
    @ApiModelProperty(value = "이메일", dataType = "string", required = true )
    @NotBlank
    @Email
    private String email;
}
