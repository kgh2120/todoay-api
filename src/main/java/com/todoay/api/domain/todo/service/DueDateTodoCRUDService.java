package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.duedate.*;
import com.todoay.api.domain.todo.utility.TodoValidator;

import java.util.List;


public interface DueDateTodoCRUDService extends TodoValidator {
    DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto);
    void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto);
    List<DueDateTodoReadResponseDto> readTodosOrderByCondition(String condition);

    DueDateTodoReadDetailResponseDto readDueDateTodoDetail(Long id);

    List<DueDateTodoReadResponseDto> readFinishedTodos();

}
