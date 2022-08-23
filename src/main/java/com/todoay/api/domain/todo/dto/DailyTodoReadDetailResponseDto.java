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
public class DailyTodoReadDetailResponseDto {


    private Long id;
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
    private CategoryInfoDto category;

    private List<HashtagInfoDto> hashtagNames = new ArrayList<>();

    public static DailyTodoReadDetailResponseDto createReadResponseDto(DailyTodo dailyTodo) {

        DailyTodoReadDetailResponseDto dto = injectTodoInfo(dailyTodo);
        injectAssociatedInfo(dto, dailyTodo);
        return dto;
    }

    private static DailyTodoReadDetailResponseDto injectTodoInfo(DailyTodo dailyTodo) {
        DailyTodoReadDetailResponseDto dto = new DailyTodoReadDetailResponseDto();
        dto.id = dailyTodo.getId();
        dto.title = dailyTodo.getTitle();
        String originDescription = dailyTodo.getDescription();
        if (isStrNotNull(originDescription))
            dto.description = originDescription;
        dto.isPublic = dailyTodo.isPublic();
        dto.isFinished = dailyTodo.isFinished();
        dto.alarm = dailyTodo.getAlarm();
        dto.targetTime = dailyTodo.getTargetTime();
        if (isStrNotNull(dailyTodo.getPeople()))
            dto.people = dailyTodo.getPeople();
        if (isStrNotNull(dailyTodo.getPlace()))
            dto.place = dailyTodo.getPlace();
        dto.dailyDate = dailyTodo.getDailyDate();
        return dto;
    }

    private static boolean isStrNotNull(String fieldValue) {
        return fieldValue != null && !fieldValue.equals("null") && !fieldValue.isBlank();
    }

    private static void injectAssociatedInfo(DailyTodoReadDetailResponseDto dto, DailyTodo dailyTodo) {
        injectCategoryInfo(dto, dailyTodo.getCategory());
        injectHashtagInfo(dto, dailyTodo.getTodoHashtags());
    }

    private static void injectCategoryInfo(DailyTodoReadDetailResponseDto dto, Category category) {
        dto.category = CategoryInfoDto.create(category);
    }

    private static void injectHashtagInfo(DailyTodoReadDetailResponseDto dto, List<TodoHashtag> hashtags) {
        dto.hashtagNames = hashtags.stream().map(h ->
                new HashtagInfoDto(h.getHashTag())
        ).collect(Collectors.toList());
    }
}
