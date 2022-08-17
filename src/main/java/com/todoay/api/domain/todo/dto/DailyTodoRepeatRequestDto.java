package com.todoay.api.domain.todo.dto;

import lombok.Data;

@Data
public class DailyTodoRepeatRequestDto {

    private String repeatType;
    private String duration;
    int repeat;
}
