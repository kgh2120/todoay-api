package com.todoay.api.domain.auth.ditio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class EmailDto {
    @Schema(required = true)
    @NotBlank
    @Email
    private String email;
}
