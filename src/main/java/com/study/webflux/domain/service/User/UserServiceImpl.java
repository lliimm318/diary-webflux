package com.study.webflux.domain.service.User;

import com.study.webflux.domain.service.Token.TokenService;
import com.study.webflux.entity.User;
import com.study.webflux.exception.InvalidTokenException;
import com.study.webflux.exception.UserAlreadyException;
import com.study.webflux.exception.UserNotFoundException;
import com.study.webflux.payload.request.AuthRequest;
import com.study.webflux.payload.response.TokenResponse;
import com.study.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public Mono register(AuthRequest authRequest) {
        User user = toUser(authRequest.getName(), authRequest.getPassword(), passwordEncoder);

        return userRepository.findByName(user.getName())
                .handle((dbUser, sink) -> {
                    sink.error(new UserAlreadyException());
                })
                .switchIfEmpty(userRepository.save(user));
    }

    @Override
    public Mono login(AuthRequest authRequest) {
        return userRepository.findByName(authRequest.getName())
                .flatMap(user -> {
                    if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                        return Mono.just(new TokenResponse(tokenService.generateAccessToken(authRequest.getName()),
                                tokenService.generateRefreshToken(authRequest.getName())));
                    } else {
                        return Mono.error(new InvalidTokenException());
                    }
                })
                .switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    @Override
    public Mono tokenRefresh(String token) {
        return null;
    }

    private User toUser(String name, String password, PasswordEncoder passwordEncoder) {
        return new User(name, passwordEncoder.encode(password));
    }
}
