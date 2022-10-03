package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.dto.duedate.*;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DueDateTodoRepository;
import com.todoay.api.domain.todo.utility.EnumTransformer;
import com.todoay.api.domain.todo.utility.HashtagAttacher;
import com.todoay.api.global.context.LoginAuthContext;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DueDateTodoCRUDServiceImpl implements DueDateTodoCRUDService {
    private final DueDateTodoRepository dueDateTodoRepository;
    private final LoginAuthContext loginAuthContext;
    private final HashtagRepository hashtagRepository;


    @Override
    public DueDateTodoSaveResponseDto addTodo(DueDateTodoSaveRequestDto dto) {
        Auth auth = getLoggedInAuth();
        DueDateTodo dueDateTodo = saveNewDueDateTodoEntity(dto, auth);
        return DueDateTodoSaveResponseDto.builder().id(dueDateTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDueDateTodo(Long id, DueDateTodoModifyRequestDto dto) {
        DueDateTodo dueDateTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dueDateTodo.modify(dto.getTitle(), dto.isPublicBool(), dto.getDueDate(), dto.getDescription(),
                (Importance) EnumTransformer.valueOfEnum(Importance.class,dto.getImportance()));
        HashtagAttacher.attachHashtag(dueDateTodo, dto.getHashtagNames(), hashtagRepository);
    }

    @Override
    public List<DueDateTodoReadResponseDto> readTodosOrderByCondition(String condition) {
        Auth loggedInAuth = getLoggedInAuth();
        List<DueDateTodo> todos = dueDateTodoRepository.findNotFinishedDueDateTodoByAuth(loggedInAuth);
        return sortDueDateTodoByCondition(condition, todos);
    }
    @Override
    public DueDateTodoReadDetailResponseDto readDueDateTodoDetail(Long id) {
        DueDateTodo todo = checkIsPresentAndIsMineAndGetTodo(id);
        return DueDateTodoReadDetailResponseDto.createDto(todo);
    }

    @Override
    public List<DueDateTodoReadResponseDto> readFinishedTodos() {
        Auth loginAuth = getLoggedInAuth();
        List<DueDateTodo> finishedTodos = dueDateTodoRepository.findFinishedDueDateTodoByAuth(loginAuth);
        return sortTodosOrderByDueDate(finishedTodos);
    }

    private List<DueDateTodoReadResponseDto> sortDueDateTodoByCondition(String condition, List<DueDateTodo> todos) {
        switch ((OrderCondition)EnumTransformer.valueOfEnum(OrderCondition.class, condition)) {
            case DUEDATE: return sortTodosOrderByDueDate(todos);
            case IMPORTANCE: return sortTodosOrderByImportance(todos);
            default: return Collections.emptyList(); // 나올 수 없는 케이스
        }
    }

    private DueDateTodo checkIsPresentAndIsMineAndGetTodo(Long id) {
        return (DueDateTodo) checkIsPresentAndIsMineAndGetTodo(id, loginAuthContext);
    }

    public DueDateTodo checkIsPresentAndGetTodo(Long id) {
        return dueDateTodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    private List<DueDateTodoReadResponseDto> sortTodosOrderByDueDate(List<DueDateTodo> todos) {
        return todos.stream()
                .sorted(Comparator.comparing(DueDateTodo::getDueDate))
                .map(DueDateTodoReadResponseDto::createDto)
                .collect(Collectors.toList());
    }
    private List<DueDateTodoReadResponseDto> sortTodosOrderByImportance(List<DueDateTodo> todos){
        return todos.stream()
                .sorted(Comparator.comparing(DueDateTodo::getImportance))
                .map(DueDateTodoReadResponseDto::createDto)
                .collect(Collectors.toList());
    }

    private DueDateTodo saveNewDueDateTodoEntity(DueDateTodoSaveRequestDto dto, Auth auth) {
        DueDateTodo dueDateTodo = createDueDateTodoEntity(dto, auth);
        dueDateTodoRepository.save(dueDateTodo);
        return dueDateTodo;
    }

    private DueDateTodo createDueDateTodoEntity(DueDateTodoSaveRequestDto dto, Auth auth) {
        DueDateTodo dueDateTodo = DueDateTodo.builder()
                .title(dto.getTitle())
                .isPublic(dto.isPublicBool())
                .isFinished(false)
                .dueDate(dto.getDueDate())
                .description(dto.getDescription())
                .importance((Importance) EnumTransformer.valueOfEnum(Importance.class, dto.getImportance()))
                .auth(auth)
                .build();

        HashtagAttacher.attachHashtag(dueDateTodo, dto.getHashtagNames(), hashtagRepository);
        return dueDateTodo;
    }

    private Auth getLoggedInAuth() {
        return loginAuthContext.getLoginAuth();
    }
}
