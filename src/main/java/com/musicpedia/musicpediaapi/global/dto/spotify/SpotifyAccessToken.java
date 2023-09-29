package com.musicpedia.musicpediaapi.global.dto.spotify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAccessToken {
    private String accessToken;

    private String tokenType;

    private Long expiresIn;
}
