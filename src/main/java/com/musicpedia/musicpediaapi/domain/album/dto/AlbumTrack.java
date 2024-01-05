package com.musicpedia.musicpediaapi.domain.album.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlbumTrack {
    private SpotifyAlbumTrack albumTrack;

    private Score score;
}
