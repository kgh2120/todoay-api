package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.todo.entity.Importance;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@Builder
public class DueDateTodoModifyRequestDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    private String description;
    private boolean isPublic = false;

    // DuedateTodo 속성
    private LocalDate dueDate;
    private Importance importance;
}
