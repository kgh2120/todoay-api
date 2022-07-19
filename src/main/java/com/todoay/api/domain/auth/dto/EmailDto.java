package com.todoay.api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class EmailDto {
    @NotBlank
    @Email
    private String email;
}
