package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.todo.entity.Todo;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.global.context.LoginAuthContext;

public interface TodoValidator {

    default Todo checkIsPresentAndIsMineAndGetTodo(Long id, LoginAuthContext loginAuthContext) {
        Todo dailyTodo = checkIsPresentAndGetTodo(id);
        checkThisTodoIsMine(dailyTodo, loginAuthContext);
        return dailyTodo;
    }

    private void checkThisTodoIsMine(Todo todo, LoginAuthContext loginAuthContext) {
        if(!todo.getAuth().equals(loginAuthContext.getLoginAuth())) throw new NotYourTodoException();
    }

    Todo checkIsPresentAndGetTodo(Long id);
}
