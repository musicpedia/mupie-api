package com.musicpedia.musicpediaapi.global.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthTokens {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String grantType;

    public static AuthTokens of(String accessToken, String refreshToken, String grantType) {
        return new AuthTokens(accessToken, refreshToken, grantType);
    }
}