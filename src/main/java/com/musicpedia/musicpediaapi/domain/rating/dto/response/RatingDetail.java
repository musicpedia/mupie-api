package com.musicpedia.musicpediaapi.domain.rating.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingDetail {
    @Schema(example = "album")
    private Type type;

    @Schema(example = "4.5")
    private String score;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String spotifyId;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String spotifyArtistId;

    @Schema(example = "Austin")
    private String name;

    @Schema(example = "https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
    private String thumbnail;

    @Schema(example = "2023-10-06")
    private String releaseDate;
}
