package com.todoay.api.domain.hashtag.entity;

import javax.persistence.*;

@Entity
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
