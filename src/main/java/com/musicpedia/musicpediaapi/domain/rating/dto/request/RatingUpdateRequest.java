package com.musicpedia.musicpediaapi.domain.rating.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingUpdateRequest {
    private String spotifyId;

    private String score;
}