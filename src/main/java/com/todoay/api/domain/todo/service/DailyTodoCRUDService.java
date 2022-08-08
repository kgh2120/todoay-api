package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.DailyTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveResponseDto;

public interface DailyTodoCRUDService {
    DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto);
    void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto);
    void deleteDailyTodo(Long id);

}
