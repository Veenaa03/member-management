package com.surest.members.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username){
        final String extracted = extractUsername(token);
        return (extracted.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }
}
