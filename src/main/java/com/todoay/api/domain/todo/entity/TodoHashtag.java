package com.todoay.api.domain.todo.entity;


import com.todoay.api.domain.hashtag.entity.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class TodoHashtag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashTag;

    @Builder
    public TodoHashtag(Todo todo, Hashtag hashTag) {
        this.todo = todo;
        this.hashTag = hashTag;
    }


}
