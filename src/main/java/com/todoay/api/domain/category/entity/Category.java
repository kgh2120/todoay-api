package com.todoay.api.domain.category.entity;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.todo.entity.DailyTodo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String color;


    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private boolean isEnded = false;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    @OneToMany(mappedBy = "category", orphanRemoval = true) // 카테고리 삭제 시 해당 카테고리 ID를 FK로 지니고 있는 dailyTodo 삭제
    List<DailyTodo> daliyTodos = new ArrayList<>();

}
