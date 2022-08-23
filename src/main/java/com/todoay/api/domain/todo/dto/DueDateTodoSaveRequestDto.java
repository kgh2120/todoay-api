package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class DueDateTodoSaveRequestDto {
    // Todo 공통 속성
    @NotNull
    private String title;
    private String description = "내용 없음";
    private boolean publicBool = false;

    // DuedateTodo 속성
    @NotNull
    private LocalDate dueDate;
    private String importance;

    // hashtag
    private List<HashtagInfoDto> hashtagNames = new ArrayList<>();
}
