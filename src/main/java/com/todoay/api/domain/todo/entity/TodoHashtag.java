package com.todoay.api.domain.todo.entity;


import javax.persistence.*;

@Entity
public class TodoHashtag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashTag;
}
