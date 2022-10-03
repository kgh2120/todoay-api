package com.todoay.api.domain.todo.utility;

import com.todoay.api.domain.todo.entity.Todo;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.global.context.LoginAuthContext;

public interface TodoValidator {

    default Todo checkIsPresentAndIsMineAndGetTodo(Long id, LoginAuthContext loginAuthContext) {
        Todo todo = checkIsPresentAndGetTodo(id);
        checkThisTodoIsMine(todo, loginAuthContext);
        return todo;
    }

    private void checkThisTodoIsMine(Todo todo, LoginAuthContext loginAuthContext) {
        if(!todo.getAuth().equals(loginAuthContext.getLoginAuth())) throw new NotYourTodoException();
    }

    Todo checkIsPresentAndGetTodo(Long id);
}
