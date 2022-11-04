package com.study.webflux.domain.service.Token;

import com.study.webflux.security.auth.AuthFacade;
import com.study.webflux.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public String getName(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private String generateToken(String name, Long exp) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(exp)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
