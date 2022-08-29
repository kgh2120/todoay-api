package com.todoay.api.domain.todo.dto.daily;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyTodoDailyDateModifyRequestDto {
    private LocalDate dailyDate;
}
