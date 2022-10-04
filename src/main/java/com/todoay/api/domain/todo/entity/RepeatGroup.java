package com.todoay.api.domain.todo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @NoArgsConstructor
public class RepeatGroup {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "repeatGroup", orphanRemoval = true)
    private List<DailyTodo> dailyTodos = new ArrayList<>();
}
