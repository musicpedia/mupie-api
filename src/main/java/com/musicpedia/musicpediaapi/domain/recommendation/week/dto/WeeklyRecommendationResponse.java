package com.musicpedia.musicpediaapi.domain.recommendation.week.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeeklyRecommendationResponse {
    private List<WeeklyRecommendationTrack> weeklyRecommendationTracks;

    private int size;
}
