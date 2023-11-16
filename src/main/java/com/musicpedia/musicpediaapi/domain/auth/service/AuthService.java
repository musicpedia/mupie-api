package com.musicpedia.musicpediaapi.domain.auth.service;

import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String GRANT_TYPE = "Bearer";
    private final JwtUtil jwtUtil;

    @Value("${guest.member-id}")
    private long guestId;

    public AuthTokens reissueTokens(AuthTokens authTokens) {
        String refreshToken = authTokens.getRefreshToken();

        jwtUtil.validateRefreshToken(refreshToken);

        Long memberId = jwtUtil.getMemberIdFromToken(refreshToken);
        return AuthTokens.of(jwtUtil.generateAccessToken(memberId), jwtUtil.generateRefreshToken(memberId), GRANT_TYPE);
    }

    public AuthTokens guestLogin() {
        return AuthTokens.of(jwtUtil.generateAccessToken(guestId), jwtUtil.generateRefreshToken(guestId), GRANT_TYPE);
    }
}
