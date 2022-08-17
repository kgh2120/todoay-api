package com.todoay.api.domain.todo.dto;

import lombok.Data;

@Data
public class DailyTodoRepeatRequestDto {

    private String type;
    private String select;
    int repeat;
}
