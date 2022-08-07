package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.todo.dto.DueDateTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveResponseDto;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DuedateTodoRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DueDateTodoCRUDServiceImpl implements DueDateTodoCRUDService {
    private final DuedateTodoRepository duedateTodoRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;


    @Override
    public DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).get();
        DueDateTodo dueDateTodo = duedateTodoRepository.save(
                DueDateTodo.builder()
                        .title(dto.getTitle())
                        .isPublic(dto.isPublic())
                        .dueDate(dto.getDueDate())
                        .description(dto.getDescription())
                        .importance(Importance.valueOf(dto.getImportance().toUpperCase()))
                        .auth(auth)
                        .build()
        );
        return DueDateTodoSaveResponseDto.builder().id(dueDateTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDueDateTodo(DueDateTodoModifyRequestDto dto) {
        DueDateTodo dueDateTodo = duedateTodoRepository.findById(dto.getId()).orElseThrow(TodoNotFoundException::new);
        if(!jwtProvider.getLoginId().equals(dueDateTodo.getAuth().getEmail())) throw new NotYourTodoException();
        dueDateTodo.modify(dto.getTitle(), dto.isPublic(), dto.getDueDate(), dto.getDescription(),dto.getImportance());
    }

    @Transactional
    public void deleteDueDateTodo(Long id) {
        DueDateTodo dueDateTodo = duedateTodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        duedateTodoRepository.delete(dueDateTodo);
    }

}
