package com.todoay.api.domain.category.entity;

import com.todoay.api.domain.auth.entity.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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


    private Integer orderIndex;

    @Column(nullable = false)
    private boolean isEnded = false;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;


}
