package com.todoay.api.domain.todo.dto.daily;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.todo.entity.DailyTodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DailyTodoReadResponseDto {

    private Long id;
    private String title;
    private boolean isPublic;
    private boolean isFinished;

    private Long categoryId;
    private Long repeatId;
    private List<HashtagInfoDto> hashtagInfoDtos = new ArrayList<>();

    public static DailyTodoReadResponseDto createDto(DailyTodo todo) {

        return DailyTodoReadResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .isPublic(todo.isPublic())
                .isFinished(todo.isFinished())
                .categoryId(todo.getCategory().getId())
                .repeatId(todo.getRepeatGroup().getId())
                .hashtagInfoDtos(todo.getTodoHashtags().stream()
                        .map(th-> new HashtagInfoDto(th.getHashTag()))
                        .collect(Collectors.toList()))
                .build();

    }


}
