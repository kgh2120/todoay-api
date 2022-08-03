package com.todoay.api.domain.todo.entity;

import com.todoay.api.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DaliyTodo extends Todo{

    @Column(nullable = false)
    private String content;
    private LocalDateTime alarm;
    private String place;
    private String people;
    @Column(nullable = false)
    private LocalDate dailyDate;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder
    public DaliyTodo(String title, boolean isPublic, String content, LocalDateTime alarm, String place, String people, LocalDate dailyDate, Category category) {
        this.title = title;
        this.isPublic = isPublic;
        this.content = content;
        this.alarm = alarm;
        this.place = place;
        this.people = people;
        this.dailyDate = dailyDate;
        this.category = category;
    }
}
