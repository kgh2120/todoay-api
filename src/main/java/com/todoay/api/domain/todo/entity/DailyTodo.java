package com.todoay.api.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todoay.api.domain.auth.entity.Auth;
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
public class DailyTodo extends Todo{
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime alarm;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime targetTime;
    private String place;
    private String people;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dailyDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder
    public DailyTodo(String title, boolean isPublic, boolean isFinished, String description,LocalDateTime targetTime,LocalDateTime alarm, String place, String people, LocalDate dailyDate, Category category, Auth auth) {
        this.title = title;
        this.isPublic = isPublic;
        this.isFinished = isFinished;
        this.description = description;
        this.targetTime= targetTime;
        this.alarm = alarm;
        this.place = place;
        this.people = people;
        this.dailyDate = dailyDate;
        this.category = category;
        this.auth = auth;

        // finished? 1. 누락되어 있었는데 이유가 있는건지
    }

    public void modify(String title, boolean isPublic, boolean isFinished, String description,LocalDateTime targetTime,LocalDateTime alarm, String place, String people, LocalDate dailyDate, Category category) {
        this.title = title;
        this.isPublic = isPublic;
        this.isFinished = isFinished;
        this.description = description;
        this.targetTime= targetTime;
        this.alarm = alarm;
        this.place = place;
        this.people = people;
        this.dailyDate = dailyDate;
        this.category = category;
    }
}
