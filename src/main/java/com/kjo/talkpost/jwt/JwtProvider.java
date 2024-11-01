package com.kjo.talkpost.jwt;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode401;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private final SecretKey secretKey;
  private final Long accessTokenValidity;
  private final Long refreshTokenValidity;
  private final RedisTemplate<String, String> redisTemplate;

  public JwtProvider(
      @Value("${jwt.secret}") final String secretKey,
      @Value("${jwt.access-token-validity}") final long accessTokenValidity,
      @Value("${jwt.refresh-token-validity}") final long refreshTokenValidity,
      RedisTemplate<String, String> redisTemplate) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenValidity = accessTokenValidity;
    this.refreshTokenValidity = refreshTokenValidity;
    this.redisTemplate = redisTemplate;
  }

  public String generateToken(Long userId, long validityMilliseconds) {
    Claims claims = Jwts.claims();

    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime tokenValidity = now.plusSeconds(validityMilliseconds / 1000);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userId.toString())
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(Date.from(tokenValidity.toInstant()))
        .setIssuer("TalkPost")
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateAccessToken(Long userId) {
    return generateToken(userId, accessTokenValidity);
  }

  public String generateRefreshToken(Long userId) {
    String refreshToken = generateToken(userId, refreshTokenValidity);
    redisTemplate
        .opsForValue()
        .set(userId.toString(), refreshToken, refreshTokenValidity, TimeUnit.MILLISECONDS);
    return refreshToken;
  }

  public Long getSubject(String token) {
    return Long.valueOf(getClaims(token).getBody().getSubject());
  }

  public Jws<Claims> getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
  }

  public boolean isTokenValid(String token) {
    try {
      Jws<Claims> claims = getClaims(token);
      Date expiredDate = claims.getBody().getExpiration();

      return expiredDate.after(new Date());
    } catch (ExpiredJwtException
        | SecurityException
        | MalformedJwtException
        | UnsupportedJwtException
        | IllegalArgumentException e) {
      throw new RuntimeException();
    }
  }

  public Long parseRefreshToken(String token) {
    if (isTokenValid(token)) {
      Claims claims = getClaims(token).getBody();
      return Long.parseLong(claims.getSubject());
    }
    throw new GlobalException(ErrorCode401.INVALID_TOKEN);
  }
}
