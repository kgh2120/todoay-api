package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.DueDateTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoReadResponseDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveResponseDto;

import java.util.List;


public interface DueDateTodoCRUDService {
    DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto);
    void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto);
    void deleteDueDateTodo(Long id);

    List<DueDateTodoReadResponseDto> readTodosOrderByDueDate();

    List<DueDateTodoReadResponseDto> readTodosOrderByImportance();

}
