package com.todoay.api.domain.todo.dto.duedate;

import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.todo.entity.DueDateTodo;
import com.todoay.api.domain.todo.entity.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data @AllArgsConstructor @NoArgsConstructor
public class DueDateTodoReadResponseDto {

    Long id;
    String title;
    LocalDate dueDate;
    Importance importance;
    List<HashtagInfoDto> hashtagInfos = new ArrayList<>();

    public static DueDateTodoReadResponseDto createDto(DueDateTodo todo) {
       return  new DueDateTodoReadResponseDto(todo.getId(), todo.getTitle(), todo.getDueDate(),todo.getImportance()
               ,todo.getTodoHashtags()
               .stream()
               .map(th -> new HashtagInfoDto(th.getHashTag())
        ).collect(Collectors.toList()));
    }



}
