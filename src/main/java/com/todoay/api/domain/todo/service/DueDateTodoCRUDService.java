package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.*;

import java.util.List;


public interface DueDateTodoCRUDService {
    DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto);
    void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto);
    void deleteDueDateTodo(Long id);
    List<DueDateTodoReadResponseDto> readTodosOrderByCondition(String condition);

    DueDateTodoReadDetailResponseDto readDueDateTodoDetail(Long id);

    List<DueDateTodoReadResponseDto> readFinishedTodos();

}
