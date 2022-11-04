package com.study.webflux.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final AuthFacade authFacade;

    @Override
    public AuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String name = authFacade.getUserName();
        return new AuthDetails(authFacade.getUser(name));
    }
}
