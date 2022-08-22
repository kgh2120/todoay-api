package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.dto.DueDateTodoReadResponseDto;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.repository.DueDateTodoRepository;
import com.todoay.api.global.context.LoginAuthContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DueDateTodoCRUDServiceImplTest {

    @Mock
    DueDateTodoRepository dueDateTodoRepository;



    @Mock
    LoginAuthContext loginAuthContext;
    @InjectMocks
    DueDateTodoCRUDServiceImpl service;

    @Test
    void readDueDateTodoOrderByDuedate() throws Exception {
        // given
        setMockObjectMethod();
        // when
        List<DueDateTodoReadResponseDto> dtos = service.readTodosOrderByDueDate();

        // then
        boolean equalResult = dtos.get(0).getDueDate().isEqual(dtos.get(1).getDueDate());
        boolean beforeResult = dtos.get(0).getDueDate().isBefore(dtos.get(1).getDueDate());
        boolean result = equalResult || beforeResult;

        assertThat(result).isTrue();
    }

    @Test
    void readDueDateTodoOrderByImportance() throws Exception {
        //given
        setMockObjectMethod();
        //when
        List<DueDateTodoReadResponseDto> todos = service.readTodosOrderByImportance();

        //then
        SoftAssertions.assertSoftly(softAssertions-> {
            softAssertions.assertThat(todos.get(0).getImportance()).isEqualTo(Importance.HIGH);
            softAssertions.assertThat(todos.get(1).getImportance()).isEqualTo(Importance.MIDDLE);
            softAssertions.assertThat(todos.get(2).getImportance()).isEqualTo(Importance.LOW);
            softAssertions.assertThat(todos.get(3).getImportance()).isEqualTo(Importance.LOW);
        });

    }

    private void setMockObjectMethod() throws Exception {
        given(dueDateTodoRepository.findAllByAuth(any()))
                .willReturn(createDueDateTodoData());
        given(loginAuthContext.getLoginAuth())
                .willReturn(createAuthData());
    }

    private Auth createAuthData() throws Exception {
        String password = new BCryptPasswordEncoder().encode("qwer1234!");
        Auth auth = Auth.builder()
                .email("test@naver.com")
                .password(password)
                .build();
        Field id = auth.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(auth,1L);
        return auth;
    }
    private List<DueDateTodo> createDueDateTodoData() throws Exception{
        List<DueDateTodo> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            DueDateTodo.DueDateTodoBuilder builder = DueDateTodo.builder()
                    .auth(null)
                    .dueDate(LocalDate.of(2022, 8, i))
                    .title("테스트" + i)
                    .description("테스트입니당" + i)
                    .isPublic(true)
                    .isFinished(false);


            if(i<3)
                builder.importance(Importance.LOW);
            else if(i==3)
                builder.importance(Importance.MIDDLE);
            else
                builder.importance(Importance.HIGH);

            DueDateTodo todo = builder.build();


            Field id = todo.getClass().getSuperclass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(todo,Integer.toUnsignedLong(i));
            list.add(todo);
        }
        return list;
    }
}