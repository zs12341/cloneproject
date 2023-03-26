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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserEnum role;

//    private Long kakaoId;

//    @Column(nullable = false, unique = true)
//    private String email;

    public User(String username, String password, UserEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String nickname, Long kakaoId, String encodedPassword, String email, UserEnum user) {
        this.username = username;
//        this.kakaoId = kakaoId;
        this.password = password;
//        this.email = email;
        this.role = role;
    }

//    public User kakaoIdUpdate(Long kakaoId) {
//        this.kakaoId = kakaoId;
//        return this;
//    }
}
