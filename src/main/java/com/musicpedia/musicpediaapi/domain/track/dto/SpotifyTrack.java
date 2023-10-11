package com.musicpedia.musicpediaapi.domain.track.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyTrack {
    @Schema(example = "11dFghVXANMlKmJXsNCbNl")
    private String id;

    private SpotifyTrackAlbum album;

    private List<SpotifyTrackArtist> artists;

    @Schema(example = "265000")
    private long durationMs;

    private String name;

    @Schema(example = "74")
    private int popularity;

    private int trackNumber;

    @Schema(example = "track")
    private String type;
}
