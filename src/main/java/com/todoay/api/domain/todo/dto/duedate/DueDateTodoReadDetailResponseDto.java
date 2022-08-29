package com.todoay.api.domain.todo.dto.duedate;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class DueDateTodoReadDetailResponseDto {

    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private boolean isFinished;

    private List<HashtagInfoDto> hashtagInfoDtos = new ArrayList<>();
    private LocalDate dueDate;
    private Importance importance;

    public static DueDateTodoReadDetailResponseDto createDto(DueDateTodo todo) {
        return DueDateTodoReadDetailResponseDto.builder()
                .title(todo.getTitle())
                .id(todo.getId())
                .description(todo.getDescription())
                .isPublic(todo.isPublic())
                .isFinished(todo.isFinished())
                .dueDate(todo.getDueDate())
                .importance(todo.getImportance())
                .hashtagInfoDtos(todo.getTodoHashtags().stream()
                        .map(todoHashtag ->
                             new HashtagInfoDto(todoHashtag.getHashTag())
                        ).collect(Collectors.toList())).build();
    }

}
