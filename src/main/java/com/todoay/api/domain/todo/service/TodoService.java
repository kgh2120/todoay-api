package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.utility.TodoValidator;

public interface TodoService extends TodoValidator {


    void switchFinishState(Long id);

    void deleteTodo(Long id);
}
