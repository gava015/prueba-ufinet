package com.ufinet.autos.springboot.webapp.springboot_web.service;

import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ufinet.autos.springboot.webapp.springboot_web.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMillis;

    @Value("${jwt.refresh-token}")
    private long refreshExpirationMillis;

    public String extractUsername(final String token) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String generateToken(final User user) {
        return buildToken(user, jwtExpirationMillis);
    }

    public String generateRefreshToken(final User user) {
        return buildToken(user, refreshExpirationMillis);
    }

    private String buildToken(final User user, final long expirationMillis) {
        if (expirationMillis <= 0) {
            throw new IllegalStateException("La duración del token debe ser mayor que cero");
        }

        final Instant now = Instant.now();
        final var builder = Jwts.builder()
                .subject(user.getUsername())
                .claim("name", user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)));

        // Los usuarios registrados ya tienen ID después de guardarse en la base de datos.
        // Omitirlo cuando aún no existe evita que la creación del token falle.
        if (user.getId() != null) {
            builder.id(user.getId().toString());
        }

        return builder
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
