package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface DailyTodoCRUDService {
    DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto);
    void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto);
    void deleteDailyTodo(Long id);

    List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date);

    DailyTodoReadResponseDto readDailyTodoById(Long id);

    // 반복 생성
    void repeatDailyTodo(Long id, DailyTodoRepeatRequestDto dto);

    void modifyDailyDate(Long id, DailyTodoDailyDateModifyRequestDto dto);
}