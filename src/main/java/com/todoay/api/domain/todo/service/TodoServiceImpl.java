package com.todoay.api.domain.todo.service;


import com.todoay.api.domain.todo.entity.Todo;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.TodoRepository;
import com.todoay.api.global.context.LoginAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Service @Transactional
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final LoginAuthContext loginAuthContext;

    @Override
    public void switchFinishState(Long id) {
        Todo todo = checkIsPresentAndIsMineAndGetTodo(id);
        todo.switchFinishState();
    }

    private Todo checkIsPresentAndIsMineAndGetTodo(Long id) {
        Todo todo = checkIsPresentAndGetTodo(id);
        checkThisTodoIsMine(todo);
        return todo;
    }

    private void checkThisTodoIsMine(Todo todo) {
        if(!todo.getAuth().equals(loginAuthContext.getLoginAuth()))throw new NotYourTodoException();
    }

    private Todo checkIsPresentAndGetTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }
}
