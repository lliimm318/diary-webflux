package com.study.webflux.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    private String name;

    private String password;

    public static User from(String name, String password, PasswordEncoder passwordEncoder) {
        return new User(name, passwordEncoder.encode(password));
    }
}
