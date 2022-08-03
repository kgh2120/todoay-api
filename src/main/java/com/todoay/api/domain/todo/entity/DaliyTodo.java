package com.todoay.api.domain.todo.entity;

import com.todoay.api.domain.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DaliyTodo extends Todo{

    private String content;
    private LocalDateTime alram;
    private String place;
    private String people;
    private LocalDate dailyDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
