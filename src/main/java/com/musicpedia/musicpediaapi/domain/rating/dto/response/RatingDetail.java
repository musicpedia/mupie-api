package com.musicpedia.musicpediaapi.domain.rating.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingDetail {
    private String type;

    private String score;

    private String spotifyId;

    private String name;

    private String thumbnail;

    private String releaseDate;
}
