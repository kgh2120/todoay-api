package com.todoay.api.domain.todo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyTodoDailyDateModifyRequestDto {
    private LocalDate dailyDate;
}
