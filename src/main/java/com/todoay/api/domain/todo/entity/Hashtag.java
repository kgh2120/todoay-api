package com.todoay.api.domain.todo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

}
