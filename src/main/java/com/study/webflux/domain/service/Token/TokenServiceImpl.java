package com.study.webflux.domain.service.Token;

import com.study.webflux.domain.service.User.AuthFacade;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Value("${auth.jwt.exp.access}")
    private Long accessExp;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    private final AuthFacade authFacade;

    @Override
    public String generateAccessToken(String name) {
        return generateToken(authFacade.getUserName(), accessExp);
    }

    @Override
    public String generateRefreshToken(String name) {
        return generateToken(name, refreshExp);
    }

    private String generateToken(String name, Long exp) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExp * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
