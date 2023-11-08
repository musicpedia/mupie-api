package com.musicpedia.musicpediaapi.domain.recommendation.week.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeeklyRecommendationDetail {
    private String spotifyId;

    private String name;

    private String artist;

    private String thumbnail;

    private Type type;
}
