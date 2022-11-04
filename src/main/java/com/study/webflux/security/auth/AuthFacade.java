package com.study.webflux.security.auth;

import com.study.webflux.entity.User;
import com.study.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserRepository userRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserName() {
        return this.getAuthentication().getName();
    }

    public User getUser(String name) {
        return userRepository.findByName(name).block();
    }
}
