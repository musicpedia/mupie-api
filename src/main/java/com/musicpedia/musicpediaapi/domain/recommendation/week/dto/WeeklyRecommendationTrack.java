package com.musicpedia.musicpediaapi.domain.recommendation.week.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeeklyRecommendationTrack {
    private SpotifyTrack spotifyTrack;

    private Score score;
}