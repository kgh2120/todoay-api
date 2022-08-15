package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.todo.dto.DailyTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveResponseDto;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DailyTodoRepository;
import com.todoay.api.global.context.LoginAuthContext;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DailyTodoCRUDServiceImpl implements DailyTodoCRUDService{

    private final DailyTodoRepository dailyTodoRepository;
    private final CategoryRepository categoryRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

    private final LoginAuthContext loginAuthContext;

    @Override
    @Transactional
    public DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).get();
        System.out.println(auth);
        DailyTodo dailyTodo = dailyTodoRepository.save(
                DailyTodo.builder()
                        .title(dto.getTitle())
                        .isPublic(dto.isPublic())
                        .isFinished(false)
                        .description(dto.getDescription())
                        .targetTime(dto.getTargetTime())
                        .alarm(dto.getAlarm())
                        .place(dto.getPlace())
                        .people(dto.getPeople())
                        .dailyDate(dto.getDailyDate())
                        .category(checkIsPresentAndIsMineGetCategory(dto.getCategoryId()))
                        .auth(auth)   // auth??
                        .build()
        );
        return DailyTodoSaveResponseDto.builder().id(dailyTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto) {
       DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
       dailyTodo.modify(dto.getTitle(), dto.isPublic(), dto.isFinished(), dto.getDescription(),
               dto.getTargetTime(), dto.getAlarm(), dto.getPlace(), dto.getPeople(), dto.getDailyDate(), checkIsPresentAndIsMineGetCategory(dto.getCategoryId()));
    }

    @Override
    @Transactional
    public void deleteDailyTodo(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndIsMineAndGetTodo(id);
        dailyTodoRepository.delete(dailyTodo);
    }

    private DailyTodo checkIsPresentAndIsMineAndGetTodo(Long id) {
        DailyTodo dailyTodo = checkIsPresentAndGetTodo(id);
        checkIsMine(dailyTodo);
        return dailyTodo;
    }

    private void checkIsMine(DailyTodo dailyTodo) {
        if(!dailyTodo.getAuth().equals(jwtProvider.getLoginId())) throw new NotYourTodoException();
    }

    private DailyTodo checkIsPresentAndGetTodo(Long id) {
        return dailyTodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    private Category checkIsPresentAndIsMineGetCategory(Long id) {
        Category category = checkIsPresentAndGetCategory(id);
        checkThisCategoryIsMine(category);
        return category;
    }

    private void checkThisCategoryIsMine(Category category) {
        if(!category.getAuth().equals(loginAuthContext.getLoginAuth())) throw new NotYourCategoryException();
    }

    private Category checkIsPresentAndGetCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}

