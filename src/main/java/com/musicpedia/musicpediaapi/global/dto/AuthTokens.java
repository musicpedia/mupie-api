package com.musicpedia.musicpediaapi.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
    private String grantType;

    public static AuthTokens of(String accessToken, String refreshToken, String grantType) {
        return new AuthTokens(accessToken, refreshToken, grantType);
    }
}