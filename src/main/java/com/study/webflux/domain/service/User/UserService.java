package com.study.webflux.domain.service.User;

import com.study.webflux.payload.request.AuthRequest;
import com.study.webflux.payload.response.TokenResponse;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono register(AuthRequest authRequest);

    Mono login(AuthRequest authRequest);

    Mono tokenRefresh(String token);

}
