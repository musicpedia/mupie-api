package com.musicpedia.musicpediaapi.domain.rating.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingCreateRequest {
    private String type;

    private String spotifyId;

    private String score;

    private String name;

    private String thumbnail;

    private String releaseDate;

    public Rating toRating() {
        return Rating.builder()
                .type(Type.valueOf(type))
                .spotifyId(spotifyId)
                .score(score)
                .name(name)
                .thumbnail(thumbnail)
                .releaseDate(releaseDate)
                .build();
    }
}
