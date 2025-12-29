package com.ticket.server.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

        private static final String SECRET_KEY = "2aa5f09623ab60959b8a71358f16ee4809eb8139a42a6f19cc97f6e52681dee6";

        private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        private static final long EXPIRATION_TIME = 1000 * 60 * 60;

        public String generateToken(String email, String role) {

                return Jwts.builder()
                                .setSubject(email)
                                .claim("role", role)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                                .signWith(key, SignatureAlgorithm.HS256)
                                .compact();
        }

        public boolean validateToken(String token) {
                try {
                        Jwts.parserBuilder()
                                        .setSigningKey(key)
                                        .build()
                                        .parseClaimsJws(token);
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }

        public String extractRole(String token) {
                Claims claims = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                return claims.get("role", String.class).toUpperCase();
        }

        public String extractEmail(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}

}
