package com.todoay.api.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DailyTodoRepeatRequestDto {


    @NotBlank
    private String repeatType;

    @NotBlank
    private String duration;
    int repeat;
}
