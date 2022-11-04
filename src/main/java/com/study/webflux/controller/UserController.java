package com.study.webflux.controller;

import com.study.webflux.domain.service.User.UserService;
import com.study.webflux.payload.request.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono register(@Valid @RequestBody AuthRequest request) {
        return userService.register(request);
    }

    @PostMapping("/auth")
    public Mono login(@Valid @RequestBody AuthRequest request) {
        return userService.login(request);
    }

}
