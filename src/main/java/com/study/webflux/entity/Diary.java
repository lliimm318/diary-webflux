package com.study.webflux.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diary")
public class Diary {

    @Id
    private UUID id;

    private String title;

    private String content;

    private String writer;

    private String weather;

    private LocalDate date;

    private Boolean locked;

    public static Diary from(String title, String content, String name, String weather, Boolean isLocked) {
        UUID uuid = UUID.randomUUID();
        return new Diary(uuid, title, content, name, weather, LocalDate.now(), isLocked);
    }
}
