package com.todoay.api.domain.todo.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import com.todoay.api.domain.todo.entity.Todo;
import com.todoay.api.domain.todo.repository.TodoRepository;
import com.todoay.api.global.context.LoginAuthContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    TodoRepository repository;

    @Mock
    LoginAuthContext authContext;

    @InjectMocks
    TodoServiceImpl service;

    @Test
    void switchFinishState_FalseToTrue_DueDateTodo() throws Exception{
        //given
        Long id = 1L;
        setMockingMethod(id);
        //when
        service.switchFinishState(id);

        Todo todo = repository.findById(id).get();
        //then
        assertThat(todo.isFinished()).isTrue();
        System.out.println(todo);

    }

    @Test
    void switchFinishState_TrueToFalse_DueDateTodo() throws Exception{
        //given
        Long id = 2L;
        setMockingMethod(id);
        //when
        service.switchFinishState(id);

        Todo todo = repository.findById(id).get();
        //then
        assertThat(todo.isFinished()).isFalse();
        System.out.println(todo);

    }

    @Test
    void switchFinishState_FalseToTrue_DailyTodo() throws Exception{
        //given
        Long id = 3L;
        setMockingMethod(id);
        //when
        service.switchFinishState(id);
        Todo todo = repository.findById(id).get();

        //then
        assertThat(todo.isFinished()).isTrue();
        System.out.println(todo);
    }

    @Test
    void switchFinishState_TrueToFalse_DailyTodo() throws Exception{
        //given
        Long id = 4L;
        setMockingMethod(id);
        //when
        service.switchFinishState(id);
        Todo todo = repository.findById(id).get();

        //then
        assertThat(todo.isFinished()).isFalse();
        System.out.println(todo);
    }

    private void setMockingMethod(long id) throws Exception{
        given(authContext.getLoginAuth())
                .willReturn(createAuthData());
        given(repository.findById(id))
                .willReturn(Optional.ofNullable(createData().get((int) (id-1))));

    }



    private List<Todo> createData() throws Exception{
        List<Todo> list = new ArrayList<>();
        Auth auth = createAuthData();

        DueDateTodo dueDateTodo_isFinished_false = createDueDateTodo(auth, false);
        injectId(dueDateTodo_isFinished_false, 1L);
        list.add(dueDateTodo_isFinished_false);

        DueDateTodo dueDateTodo_isFinished_True = createDueDateTodo(auth, true);
        injectId(dueDateTodo_isFinished_True, 2L);
        list.add(dueDateTodo_isFinished_True);


        DailyTodo dailyTodo_isFinished_false = createDailyTodo(auth, false);
        injectId(dailyTodo_isFinished_false,3L);
        list.add(dailyTodo_isFinished_false);

        DailyTodo dailyTodo_isFinished_true = createDailyTodo(auth, true);
        injectId(dailyTodo_isFinished_true,4L);
        list.add(dailyTodo_isFinished_true);
        return list;
    }

    private DailyTodo createDailyTodo(Auth auth, boolean isFinished) {
        return DailyTodo.builder()
                .title("데일리투두테스트")
                .description("테스트테스트")
                .isPublic(true)
                .people("me")
                .place("home")
                .dailyDate(LocalDate.now())
                .targetTime(LocalDateTime.now())
                .alarm(LocalDateTime.now())
                .auth(auth)
                .category(null)
                .isFinished(isFinished)
                .build();
    }

    private DueDateTodo createDueDateTodo(Auth auth, boolean isFinished) {
        return DueDateTodo.builder()
                .dueDate(LocalDate.of(2022, 8, 6))
                .title("듀데이트 테스트")
                .description("테스트입니당")
                .isPublic(true)
                .auth(auth)
                .importance(Importance.LOW)
                .isFinished(isFinished)
                .build();
    }

    private void injectId(Todo todo, Long injectId) throws NoSuchFieldException, IllegalAccessException {
        Field id = todo.getClass().getSuperclass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(todo, injectId);
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
}