package com.musicpedia.musicpediaapi.domain.like.artist.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.like.artist.entity.LikedArtist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikedArtistCreateRequest {
    @Schema(example = "0TnOYISbd1XYRBk9myaseg")
    private String spotifyId;

    @Schema(example = "Post Malone")
    private String name;

    @Schema(example = "https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
    private String thumbnail;

    public LikedArtist toLikedArtist() {
        return LikedArtist.builder()
                .spotifyId(spotifyId)
                .name(name)
                .thumbnail(thumbnail)
                .build();
    }
}
