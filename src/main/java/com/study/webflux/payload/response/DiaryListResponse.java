package com.study.webflux.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DiaryListResponse {

    private UUID id;

    private String title;

    private Boolean locked;

}
