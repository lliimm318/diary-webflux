package com.study.webflux.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DiaryRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String weather;

    @NotNull
    private Boolean isLocked;

}
