package com.study.webflux.domain.service.Token;

public interface TokenService {

    String  generateAccessToken(String name);

    String  generateRefreshToken(String name);

    Boolean validToken(String token);

    String getName(String token);

}
