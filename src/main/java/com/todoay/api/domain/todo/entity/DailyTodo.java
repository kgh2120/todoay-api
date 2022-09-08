package com.todoay.api.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.todo.dto.daily.DailyTodoModifyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class DailyTodo extends Todo implements Cloneable{
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
    public void modify(DailyTodoModifyRequestDto dto,Category category) {
        this.title = dto.getTitle();
        this.isPublic = dto.isPublicBool();
        this.description = dto.getDescription();
        this.targetTime= dto.getTargetTime();
        this.alarm = dto.getAlarm();
        this.place = dto.getPlace();
        this.people = dto.getPeople();
        this.dailyDate = dto.getDailyDate();
        this.category = category;
    }

    @Override
    public String toString() {
        return "DailyTodo{" +
                "alarm=" + alarm +
                ", targetTime=" + targetTime +
                ", place='" + place + '\'' +
                ", people='" + people + '\'' +
                ", dailyDate=" + dailyDate +
                ", category=" + category +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", isFinished=" + isFinished +
                ", auth=" + auth +
                ", todoHashtags=" + todoHashtags +
                '}';
    }

    @Override
    public Object clone()  {
        DailyTodoBuilder builder = DailyTodo.builder()
                .title(this.title)
                .description(this.description)
                .isFinished(this.isFinished)
                .isPublic(this.isPublic)
                .place(this.place)
                .people(this.people)
                .auth(auth)
                .category(category);

        if(alarm != null)
            builder.alarm(LocalDateTime.of(dailyDate,LocalTime.of(alarm.getHour(),alarm.getMinute())));
        if (targetTime!= null)
            builder.targetTime(LocalDateTime.of(dailyDate,LocalTime.of(targetTime.getHour(),targetTime.getMinute())));
        return builder.build();
    }

    public void changeDailyDate(LocalDate dailyDate) {
        this.dailyDate = dailyDate;
    }
    public void changeDateForRepeat(LocalDate dailyDate) {
        this.dailyDate = dailyDate;
        if(this.alarm != null)
            this.alarm = LocalDateTime.of(dailyDate, LocalTime.of(this.alarm.getHour(),alarm.getMinute()));
        if (this.targetTime != null) {
            this.targetTime = LocalDateTime.of(dailyDate, LocalTime.of(this.targetTime.getHour(),targetTime.getMinute()));
        }
    }

}
