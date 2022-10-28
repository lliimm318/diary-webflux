package com.study.webflux.domain.service.User;

import com.study.webflux.entity.User;
import com.study.webflux.exception.UserNotFoundException;
import com.study.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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

    public Mono<User> getUser() {
        return userRepository.findById(getUserName())
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }
}
