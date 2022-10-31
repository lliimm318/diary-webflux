package com.study.webflux.domain.service.Token;

public interface TokenService {

    String  generateAccessToken(String name);

    String  generateRefreshToken(String name);

    String getName(String token);

}
