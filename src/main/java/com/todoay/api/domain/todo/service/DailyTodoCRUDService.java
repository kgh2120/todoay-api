package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.DailyTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoReadResponseDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface DailyTodoCRUDService {
    DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto);
    void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto);
    void deleteDailyTodo(Long id);

    List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date);

    DailyTodoReadResponseDto readDailyTodoById(Long id);

}
