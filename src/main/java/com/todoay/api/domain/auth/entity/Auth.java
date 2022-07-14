package com.todoay.api.domain.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO, TABLE, SEQUENCE
    @Column(name = "auth_id")
    private Long id;

    private String email;

    private String password;

}
