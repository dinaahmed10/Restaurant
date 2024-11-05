package com.Application.Restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name="person")
    public class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name ="user_name")
        private String username;
        @Column(name ="password")
        private String password;
       // @Column(name="email_id",nullable = false ,unique = true)
        private String email;
}
