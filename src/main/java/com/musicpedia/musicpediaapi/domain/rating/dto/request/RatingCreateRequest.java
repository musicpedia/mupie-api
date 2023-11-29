package com.musicpedia.musicpediaapi.domain.rating.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingCreateRequest {
    @Schema(example = "album")
    private Type type;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    @NotBlank
    private String spotifyId;

    @Schema(example = "4.5")
    @NotBlank
    private String score;

    @Schema(example = "Austin")
    @NotBlank
    private String name;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    @NotBlank
    private String spotifyArtistId;

    @Schema(example = "https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
    private String thumbnail;

    @Schema(example = "2023-10-06")
    private String releaseDate;

    public Rating toRating() {
        return Rating.builder()
                .type(type)
                .spotifyId(spotifyId)
                .score(score)
                .name(name)
                .thumbnail(thumbnail)
                .releaseDate(releaseDate)
                .build();
    }
}
