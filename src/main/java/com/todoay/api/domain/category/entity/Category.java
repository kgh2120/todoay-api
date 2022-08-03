package com.todoay.api.domain.category.entity;

import com.todoay.api.domain.auth.entity.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String color;
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;
}
