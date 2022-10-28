package com.study.webflux.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AuthRequest {

    @NotNull
    private String name;

    @NotNull
    private String password;

}
