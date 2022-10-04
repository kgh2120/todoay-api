package com.todoay.api.domain.todo.repository;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.entity.DailyTodo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Transactional
class DailyTodoRepositoryTest {

    @Autowired DailyTodoRepository repository;
    @Autowired
    AuthRepository authRepository;

    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    CategoryRepository categoryRepository;

    Long id;

    @BeforeEach
    void beforeEach() {

        Category ca = Category.builder()
                .name("cateogry")
                .color("#123123")
                .orderIndex(1)
                .build();
        categoryRepository.save(ca);

        Auth auth = authRepository.findByEmail("test@naver.com").get();
        DailyTodo todo = DailyTodo.builder()
                .title("title")
                .auth(auth)
                .category(ca)
                .dailyDate(LocalDate.now()).build();

        Slice<Hashtag> tags = hashtagRepository.findHashtagByNameContaining("#태그", PageRequest.of(0,5));

        todo.associateWithHashtag(tags.getContent());

        System.out.println("[TODO-READY]"+todo);

        DailyTodo save = repository.save(todo);
        id = save.getId();
        System.out.println("[TODO-SAVE]"+save);

    }
//    @AfterEach
//    void afterEach() {
//        repository.deleteAll();
//    }

    @Test
    void findByDailyDate() {
        Auth auth = authRepository.findByEmail("test@naver.com").get();
        List<DailyTodo> todos = repository.findDailyTodoOfUserByDate(LocalDate.now(),auth);

        for (DailyTodo todo : todos) {
            System.out.println(todo);
        }

        assertThat(todos.size()).isSameAs(1);
    }

    @Test
    void findById() {
        System.out.println("[ID]"+id);
        DailyTodo dailyTodo = repository.findDailyTodoById(id).get();
        assertThat(dailyTodo.getTitle()).isEqualTo("title");
    }
}