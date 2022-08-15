package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.DueDateTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveResponseDto;


public interface DueDateTodoCRUDService {
    DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto);
    void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto);
    void deleteDueDateTodo(Long id);

}
