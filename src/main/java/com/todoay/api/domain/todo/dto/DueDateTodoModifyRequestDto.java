package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.todo.entity.Importance;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DueDateTodoModifyRequestDto {
    @NotNull
    private String title;
    private String description = "내용 없음";
    private boolean isPublic = false;
    private boolean isFinished = false;

    // DuedateTodo 속성
    @NotNull
    private LocalDate dueDate;
    private String importance;

    // hashtag
    private List<HashtagInfoDto> hashtagNames = new ArrayList<>();
}
