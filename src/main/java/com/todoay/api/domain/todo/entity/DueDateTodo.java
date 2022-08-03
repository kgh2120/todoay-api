package com.todoay.api.domain.todo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class DueDateTodo extends Todo{

    private LocalDate dueDate;
    private String description;
    @Enumerated(value = EnumType.ORDINAL) // 0 : High, 1 : Middle, 2 : LOW
    private Importance importance;

}
