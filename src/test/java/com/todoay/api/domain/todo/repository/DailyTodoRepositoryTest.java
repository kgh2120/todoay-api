package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.entity.DailyTodo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DailyTodoRepositoryTest {

    @Autowired DailyTodoRepository repository;
    @Autowired
    AuthRepository authRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    Long id;

    @BeforeEach
    void beforeEach() {

        Auth auth = authRepository.findByEmail("test@naver.com").get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, null, new ArrayList<>()));


        DailyTodo todo = DailyTodo.builder()
                .title("title")
                .auth(auth)
                .dailyDate(LocalDate.now()).build();

        List<Hashtag> tags = hashtagRepository.findTop5ByNameStartsWith("#태그");

        todo.associateWithHashtag(tags);

        System.out.println(todo);

        DailyTodo save = repository.save(todo);
        id = save.getId();
        System.out.println(save);

    }
    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void findByDailyDate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Auth auth = (Auth) authentication.getPrincipal();
        List<DailyTodo> todos = repository.findDailyTodoOfUserByDate(LocalDate.now(),auth);

        for (DailyTodo todo : todos) {
            System.out.println(todo);
        }

        assertThat(todos.size()).isSameAs(1);
    }

    @Test
    void findById() {
        DailyTodo dailyTodo = repository.findDailyTodoById(id).get();
        assertThat(dailyTodo.getTitle()).isEqualTo("title");
    }
}