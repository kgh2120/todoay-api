package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class DailyTodoSaveRequestDto {
    // 투두 공통 속성
    @NotNull
    private String title;
    private String description = "내용 없음";
    private boolean isPublic = false;

    // DailyTodo 속성
    private LocalDateTime alarm;
    private LocalDateTime targetTime;
    private String place;
    private String people;

    @NotNull
    private LocalDate dailyDate;
    @NotNull
    private Long categoryId;

    // hashtag
    private List<HashtagInfoDto> hashtagNames = new ArrayList<>();


}
