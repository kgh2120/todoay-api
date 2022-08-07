package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.category.entity.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class DailyTodoModifyRequestDto {
    // Todo 공통 속성
    @NotNull
    private Long id;
    @NotNull
    private String title;
    private String description;

    // isPublic으로 한 경우, @Getter 사용시 제한이 있음.
    // 따라서 isPublic -> publicBool
    private boolean isPublic = false;
    private boolean isFinished = false;

    // DailyTodo 속성
    private LocalDateTime alarm;
    private LocalDateTime targetTime;
    private String place;
    private String people;
    private LocalDate dailyDate;
    private Category category;
}
