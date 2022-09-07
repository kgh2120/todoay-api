package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.todo.dto.daily.*;
import com.todoay.api.domain.todo.utility.TodoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.List;

public interface DailyTodoCRUDService extends TodoValidator {
    DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto);
    void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto);
    void deleteDailyTodo(Long id);

    List<DailyTodoReadResponseDto> readDailyTodosByDate(LocalDate date);

    DailyTodoReadDetailResponseDto readDailyTodoDetailById(Long id);

    // 반복 생성
    ListenableFuture<ResponseEntity<Void>> repeatDailyTodo(Long id, DailyTodoRepeatRequestDto dto);

    void modifyDailyDate(Long id, DailyTodoDailyDateModifyRequestDto dto);
}