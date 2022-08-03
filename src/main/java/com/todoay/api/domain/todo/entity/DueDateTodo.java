package com.todoay.api.domain.todo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class DueDateTodo extends Todo{

    @Column(nullable = false)
    private LocalDate dueDate;
    private String description;
    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL) // 0 : High, 1 : Middle, 2 : LOW
    private Importance importance;

    public DueDateTodo(String title, boolean isPublic,LocalDate dueDate, String description, Importance importance) {
        this.title = title;
        this.isPublic = isPublic;
        this.dueDate = dueDate;
        this.description = description;
        this.importance = importance;
    }
}
