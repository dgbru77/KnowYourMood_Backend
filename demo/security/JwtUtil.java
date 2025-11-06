package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

	  private final Key key;
	    private final long expirationTime;

	    // 🟢 Spring automatically injects the values from application.properties
	    public JwtUtil(
	            @Value("${app.jwt.secret}") String secret,
	            @Value("${app.jwt.expiration}") long expirationTime
	    ) {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	        this.expirationTime = expirationTime;
	    }

    // 🟢 store userId as the subject
    public String generateToken(String string) {
        return Jwts.builder()
                .setSubject(String.valueOf(string))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
