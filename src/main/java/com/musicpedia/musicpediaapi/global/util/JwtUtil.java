package com.musicpedia.musicpediaapi.global.util;

import com.musicpedia.musicpediaapi.global.exception.ExpiredAccessTokenException;
import com.musicpedia.musicpediaapi.global.exception.ExpiredRefreshTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private static final long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 2;
    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 14;
    private final Key secretKey;
    private final JwtParser jwtParser;

    public JwtUtil(@Value("${jwt.secret-key}") String jwtSecret) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }
    public String generateAccessToken(Long memberId) {
        return Jwts.builder()
                .setSubject(memberId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Long memberId) {
        return Jwts.builder()
                .setSubject(memberId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDATION_SECOND))
                .signWith(secretKey)
                .compact();
    }

    public Long getMemberIdFromToken(String token) {
        try {
            Claims claims = jwtParser
                    .parseClaimsJws(token)
                    .getBody();
            return Long.valueOf(claims.getSubject());
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException("Access Token이 만료되었습니다.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Token이 유효하지 않습니다.");
        }
    }

    public void validateRefreshToken(String refreshToken) {
        try {
            jwtParser.parseClaimsJws(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
        }
    }

    public String resolveToken(String bearerToken) {
        String token = bearerToken.replace("Bearer ", "").trim();
        if (token.isEmpty()) {
            throw new IllegalArgumentException("토큰의 헤더가 적절하지 않습니다.");
        }
        return token;
    }
}
