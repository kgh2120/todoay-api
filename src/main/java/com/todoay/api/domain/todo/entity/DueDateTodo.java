package com.todoay.api.domain.todo.entity;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DueDateTodo extends Todo{

    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Importance importance;

    @Builder
    public DueDateTodo(String title, boolean isPublic, boolean isFinished, LocalDate dueDate, String description, Importance importance, Auth auth) {
        this.title = title;
        this.isPublic = isPublic;
        this.isFinished = isFinished;
        this.dueDate = dueDate;
        this.description = description;
        this.importance = importance;
        this.auth = auth;
    }

    public void modify(String title, boolean isPublic,boolean isFinished, LocalDate dueDate, String description, Importance importance) {
        this.title = title;
        this.isPublic = isPublic;
        this.isFinished = isFinished;
        this.dueDate = dueDate;
        this.description = description;
        this.importance = importance;

    }
}
