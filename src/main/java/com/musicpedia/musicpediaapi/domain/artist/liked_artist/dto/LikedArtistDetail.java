package com.musicpedia.musicpediaapi.domain.artist.liked_artist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikedArtistDetail {
    @Schema(example = "artist")
    private String type;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String spotifyId;

    @Schema(example = "Austin")
    private String name;

    @Schema(example = "https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
    private String thumbnail;
}
