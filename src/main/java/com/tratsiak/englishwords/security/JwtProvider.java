package com.tratsiak.englishwords.security;

import io.jsonwebtoken.Claims;

public interface JwtProvider {
    String generateAccessToken(long id);

    String generateRefreshToken(long id);

    Claims getAccessClaims(String token) throws JwtProviderException;

    Claims getRefreshClaims(String token) throws JwtProviderException;

}
