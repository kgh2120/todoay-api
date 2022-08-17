package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.todo.entity.DailyTodo;
import com.todoay.api.domain.todo.entity.TodoHashtag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DailyTodoReadResponseDto {

    @NotNull
    private String title;
    private String description = "내용 없음";
    private boolean isPublic = false;
    private boolean isFinished = false;

    // DailyTodo 속성
    private LocalDateTime alarm;
    private LocalDateTime targetTime;
    private String place;
    private String people;
    @NotNull
    private LocalDate dailyDate;
    private CategoryInfoDto categoryInfoDto;

    private List<HashtagInfoDto> hashtagNames = new ArrayList<>();

    public static DailyTodoReadResponseDto createReadResponseDto(DailyTodo dailyTodo) {

        DailyTodoReadResponseDto dto = injectTodoInfo(dailyTodo);
        injectAssociatedInfo(dto, dailyTodo);
        return dto;
    }

    private static DailyTodoReadResponseDto injectTodoInfo(DailyTodo dailyTodo) {
        DailyTodoReadResponseDto dto = new DailyTodoReadResponseDto();
        dto.title = dailyTodo.getTitle();
        String originDescription = dailyTodo.getDescription();
        if (!isStrNull(originDescription))
            dto.description = originDescription;
        dto.isPublic = dailyTodo.isPublic();
        dto.isFinished = dailyTodo.isFinished();
        dto.alarm = dailyTodo.getAlarm();
        dto.targetTime = dailyTodo.getTargetTime();
        if (!isStrNull(dailyTodo.getPeople()))
            dto.people = dailyTodo.getPeople();
        if (!isStrNull(dailyTodo.getPlace()))
            dto.place = dailyTodo.getPlace();
        dto.dailyDate = dailyTodo.getDailyDate();
        return dto;
    }

    private static boolean isStrNull(String fieldValue) {
        return fieldValue == null || fieldValue.equals("null") || fieldValue.isBlank();
    }

    private static void injectAssociatedInfo(DailyTodoReadResponseDto dto, DailyTodo dailyTodo) {
        injectCategoryInfo(dto, dailyTodo.getCategory());
        injectHashtagInfo(dto, dailyTodo.getTodoHashtags());
    }

    private static void injectCategoryInfo(DailyTodoReadResponseDto dto, Category category) {
        dto.categoryInfoDto = CategoryInfoDto.create(category);
    }

    private static void injectHashtagInfo(DailyTodoReadResponseDto dto, List<TodoHashtag> hashtags) {
        dto.hashtagNames = hashtags.stream().map(h ->
                new HashtagInfoDto(h.getHashTag())
        ).collect(Collectors.toList());
    }
}
