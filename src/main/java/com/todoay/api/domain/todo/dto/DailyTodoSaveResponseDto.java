package com.todoay.api.domain.todo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyTodoSaveResponseDto {
    private Long id;
}
