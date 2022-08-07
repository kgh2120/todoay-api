package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//@Transactional(readOnly = true)  이거 하는 이유?
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
}
