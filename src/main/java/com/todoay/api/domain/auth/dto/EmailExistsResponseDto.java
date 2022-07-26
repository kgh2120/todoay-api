package com.todoay.api.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailExistsResponseDto {
    private boolean emailExists;
}
