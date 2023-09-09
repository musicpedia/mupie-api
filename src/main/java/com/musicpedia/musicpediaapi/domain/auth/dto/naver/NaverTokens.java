package com.musicpedia.musicpediaapi.domain.auth.dto.naver;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverTokens {
    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private String expiresIn;
}
