package com.study.webflux.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class DiaryResponse {

    private UUID id;

    private String title;

    private String content;

    private String writer;

    private String weather;

    private LocalDate date;

    private Boolean locked;

    private String comment;

}
