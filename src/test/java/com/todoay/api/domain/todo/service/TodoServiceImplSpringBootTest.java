package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceImplSpringBootTest {

    @Autowired TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    AuthRepository authRepository;

    Long dailyId;
    Long dueId;

    @BeforeEach
    void before_each() {
        Auth auth = authRepository.findByEmail("test@naver.com").get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, null, null));
        DailyTodo dailyTodo = DailyTodo.builder()
                .title("title_1")
                .description("desc_1")
                .dailyDate(LocalDate.now())
                .isFinished(false)
                .isPublic(false)
                .auth(auth)
                .build();

        DueDateTodo due = DueDateTodo.builder()
                .title("due_title_2")
                .description("desc_2")
                .importance(Importance.LOW)
                .dueDate(LocalDate.now())
                .auth(auth)
                .isFinished(false)
                .isPublic(false)
                .build();

        DailyTodo d1 = todoRepository.save(dailyTodo);
        dailyId = d1.getId();
        DueDateTodo d2 = todoRepository.save(due);
        dueId = d2.getId();
    }

    @AfterEach
    void after_each() {
        todoRepository.deleteAll();
    }

    @Test
    void deleteTodo_DailyTodo () throws Exception{
        //given
        Long id = dailyId;
        //when
        todoService.deleteTodo(id);
        //then
        assertThatThrownBy(() -> todoRepository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteTodo_DueDateTodo () throws Exception{
        //given
        Long id = dueId;
        //when
        todoService.deleteTodo(id);
        //then
        assertThatThrownBy(() -> todoRepository.findById(id).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }


}