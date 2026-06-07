package com.chat.gusta.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_STRING =
            "MinhaChaveSuperSecretaQuePrecisaSerLonga123!";

    private static final long EXPIRATION_TIME = 864_000_000; // 10 dias

    private final Key key =
            Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // 🔑 GERA TOKEN
    public String generateToken(Long userId, String nome, String role) {

        String token = Jwts.builder()
                .setSubject(userId.toString()) // ID DO USUÁRIO
                .claim("role", role)
                .claim("nome", nome)
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 🔍 DEBUG
        System.out.println("===== JWT GERADO =====");
        System.out.println("TOKEN: " + token);
        System.out.println("USER ID: " + userId);
        System.out.println("ROLE: " + role);
        System.out.println("======================");

        return token;
    }

    // 🔓 PARSE TOKEN
    public Claims parseToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 🔍 DEBUG
        System.out.println("===== JWT PARSE =====");
        System.out.println("SUBJECT (ID): " + claims.getSubject());
        System.out.println("ROLE: " + claims.get("role"));
        System.out.println("NOME: " + claims.get("nome"));
        System.out.println("EXPIRATION: " + claims.getExpiration());
        System.out.println("=====================");

        return claims;
    }
}
