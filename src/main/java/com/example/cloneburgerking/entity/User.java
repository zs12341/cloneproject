package com.example.cloneburgerking.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String nickname;
    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserEnum role;


    public User(String username, String password, UserEnum role, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nickname = nickname;
        this.email = email;
    }



}
