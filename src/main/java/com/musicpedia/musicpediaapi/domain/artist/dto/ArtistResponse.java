package com.musicpedia.musicpediaapi.domain.artist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArtistResponse {
    private SpotifyArtist spotifyArtist;

    private boolean like;
}
