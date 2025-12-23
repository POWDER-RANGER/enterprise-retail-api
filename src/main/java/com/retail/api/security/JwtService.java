package com.retail.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

  private final SecretKey key;
  private final String issuer;
  private final long accessTokenMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.issuer:retail-api}") String issuer,
      @Value("${app.jwt.accessTokenMinutes:60}") long accessTokenMinutes
  ) {
    if (secret == null || secret.length() < 32) {
      throw new IllegalArgumentException("app.jwt.secret must be at least 32 characters for HS256");
    }
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.issuer = issuer;
    this.accessTokenMinutes = accessTokenMinutes;
  }

  public String generateAccessToken(String username, String role) {
    Instant now = Instant.now();
    Instant exp = now.plus(accessTokenMinutes, ChronoUnit.MINUTES);

    return Jwts.builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .claim("role", role)
        .signWith(key)
        .compact();
  }

  public Claims parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public String extractUsername(String token) {
    return parseClaims(token).getSubject();
  }

  public String extractRole(String token) {
    Object role = parseClaims(token).get("role");
    return role == null ? null : role.toString();
  }
}
