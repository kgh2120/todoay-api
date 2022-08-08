package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.todo.dto.DailyTodoModifyRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveResponseDto;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.exception.NotYourTodoException;
import com.todoay.api.domain.todo.exception.TodoNotFoundException;
import com.todoay.api.domain.todo.repository.DailyTodoRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DailyTodoCRUDServiceImpl implements DailyTodoCRUDService{

    private final DailyTodoRepository dailyTodoRepository;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public DailyTodoSaveResponseDto addTodo(DailyTodoSaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).get();
        System.out.println(auth);
        DailyTodo dailyTodo = dailyTodoRepository.save(
                DailyTodo.builder()
                        .title(dto.getTitle())
                        .isPublic(dto.isPublic())
                        .isFinished(dto.isFinished())
                        .description(dto.getDescription())
                        .targetTime(dto.getTargetTime())
                        .alarm(dto.getAlarm())
                        .place(dto.getPlace())
                        .people(dto.getPeople())
                        .dailyDate(dto.getDailyDate())
                        .category(dto.getCategory())
                        .auth(auth)   // auth??
                        .build()
        );
        return DailyTodoSaveResponseDto.builder().id(dailyTodo.getId()).build();
    }

    @Override
    @Transactional
    public void modifyDailyTodo(Long id, DailyTodoModifyRequestDto dto) {
        DailyTodo dailyTodo = dailyTodoRepository.findById(dto.getId()).orElseThrow(TodoNotFoundException::new);
                if(!jwtProvider.getLoginId().equals(dailyTodo.getAuth().getEmail())) throw new NotYourTodoException();
                dailyTodo.modify(dto.getTitle(), dto.isPublic(), dto.isFinished(), dto.getDescription(),
                        dto.getTargetTime(), dto.getAlarm(), dto.getPlace(), dto.getPeople(), dto.getDailyDate(), dto.getCategory());
    }

    @Override
    @Transactional
    public void deleteDailyTodo(Long id) {
        DailyTodo dailyTodo = dailyTodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        dailyTodoRepository.delete(dailyTodo);
    }
}

