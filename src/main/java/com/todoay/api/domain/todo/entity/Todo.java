package com.todoay.api.domain.todo.entity;

import com.todoay.api.domain.auth.entity.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
public abstract class Todo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY)
    private List<TodoHashtag> todoHashtags = new ArrayList<>();
}
