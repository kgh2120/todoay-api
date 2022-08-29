package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.dto.daily.DailyTodoDailyDateModifyRequestDto;
import com.todoay.api.domain.todo.dto.daily.DailyTodoReadDetailResponseDto;
import com.todoay.api.domain.todo.dto.daily.DailyTodoReadResponseDto;
import com.todoay.api.domain.todo.dto.daily.DailyTodoRepeatRequestDto;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.repository.DailyTodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DailyTodoCRUDServiceImplTest {

    @Autowired
    DailyTodoCRUDService dailyTodoCRUDService;
    @Autowired
    DailyTodoRepository repository;

    @Autowired
    AuthRepository authRepository;
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    CategoryRepository categoryRepository;

    Long id;

    @BeforeEach
    void beforeEach() {

        Auth auth = authRepository.findByEmail("test@naver.com").get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, null, new ArrayList<>()));


        Category category = Category.builder()
                .auth(auth)
                .color("123123")
                .name("테스트")
                .orderIndex(3)
                .build();

        categoryRepository.save(category);

        DailyTodo todo = DailyTodo.builder()
                .title("title")
                .auth(auth)
                .dailyDate(LocalDate.now())
                .category(category).build();

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
        categoryRepository.deleteAll();
    }

    @Test
    void readDailyDateByLocaldateTest() {

        List<DailyTodoReadResponseDto> dtos = dailyTodoCRUDService.readDailyTodosByDate(LocalDate.now());
        for (DailyTodoReadResponseDto dto : dtos) {
            System.out.println(dto);
        }
        assertThat(dtos.size()).isSameAs(1);
    }

    @Test
    void readDailyDateById() {

        DailyTodoReadDetailResponseDto dto = dailyTodoCRUDService.readDailyTodoDetailById(id);
        System.out.println(dto);
        assertThat(dto.getTitle()).isEqualTo("title");
    }


    @Test
    void repeatTest() {
        DailyTodoRepeatRequestDto dto = new DailyTodoRepeatRequestDto();
        dto.setRepeat(5);
        dto.setDuration("custom_number");
        dto.setRepeatType("weeks");

        dailyTodoCRUDService.repeatDailyTodo(id,dto);

        long count = repository.count();
        assertThat(count).isSameAs(6L);

    }

    @Test
    void modifyDailyDate() {
        DailyTodoDailyDateModifyRequestDto dto = new DailyTodoDailyDateModifyRequestDto();
        dto.setDailyDate(LocalDate.now());
        dailyTodoCRUDService.modifyDailyDate(id, dto);
        DailyTodoReadDetailResponseDto responseDto = dailyTodoCRUDService.readDailyTodoDetailById(id);
        assertThat(responseDto.getDailyDate()).isEqualTo(LocalDate.now());
    }
    
    

}