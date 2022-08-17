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
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashTag;

    @Builder
    public TodoHashtag(Todo todo, Hashtag hashTag) {
        this.todo = todo;
        this.hashTag = hashTag;
    }


    @Override
    public String toString() {
        return "TodoHashtag{" +
                "id=" + id +
                ", hashTag=" + hashTag +
                '}';
    }
}
