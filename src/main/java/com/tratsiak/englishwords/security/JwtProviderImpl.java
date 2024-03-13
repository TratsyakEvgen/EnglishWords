package com.tratsiak.englishwords.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProviderImpl implements JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProviderImpl() {
        this.jwtAccessSecret = Jwts.SIG.HS256.key().build();
        this.jwtRefreshSecret = Jwts.SIG.HS256.key().build();
    }

    @Override
    public String generateAccessToken(long id) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .signWith(jwtAccessSecret)
                .claim("id", id)
                .compact();
    }

    @Override
    public String generateRefreshToken(long id) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .signWith(jwtRefreshSecret)
                .claim("id", id)
                .compact();
    }

    @Override
    public Claims getAccessClaims(String token) throws JwtProviderException {
        return getClaims(token, jwtAccessSecret);
    }

    @Override
    public Claims getRefreshClaims(String token) throws JwtProviderException {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, SecretKey secret) throws JwtProviderException {
        try {
            return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new JwtProviderException("Token is not valid", e);
        }
    }

}
