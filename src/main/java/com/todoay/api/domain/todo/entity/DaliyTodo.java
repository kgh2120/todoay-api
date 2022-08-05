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

    private LocalDateTime alarm;

    private LocalDateTime targetTime;
    private String place;
    private String people;
    @Column(nullable = false)
    private LocalDate dailyDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @Builder
    public DaliyTodo(String title, boolean isPublic,String description,LocalDateTime targetTime,LocalDateTime alarm, String place, String people, LocalDate dailyDate, Category category) {
        this.title = title;
        this.isPublic = isPublic;
        this.description = description;
        this.targetTime= targetTime;
        this.alarm = alarm;
        this.place = place;
        this.people = people;
        this.dailyDate = dailyDate;
        this.category = category;
    }
}
