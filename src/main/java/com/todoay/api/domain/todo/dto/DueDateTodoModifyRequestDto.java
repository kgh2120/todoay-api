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
    private String title;
    private String description = "내용 없음";
    private boolean isPublic = false;
    private boolean isFinished = false;

    // DuedateTodo 속성
    private LocalDate dueDate;
    private Importance importance;
}
