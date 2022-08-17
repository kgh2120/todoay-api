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

    // 날짜에 해당하는 DailyTodo를 전부 불러온다.
    List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date);

    // 특정 id를 통해 DailyTodo의 정보를 불러온다
    DailyTodoReadResponseDto readDailyTodoById(Long id);

}
