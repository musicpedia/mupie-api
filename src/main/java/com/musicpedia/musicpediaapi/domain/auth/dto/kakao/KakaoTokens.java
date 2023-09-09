package com.musicpedia.musicpediaapi.domain.auth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokens {
    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private String expiresIn;

    private String refreshTokenExpiresIn;

    private String scope;
}
