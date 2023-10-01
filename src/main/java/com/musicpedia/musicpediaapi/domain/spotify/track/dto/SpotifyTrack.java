package com.musicpedia.musicpediaapi.domain.spotify.track.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyTrack {
    private String id;

    private SpotifyTrackAlbum album;

    private List<SpotifyTrackArtist> artists;

    private long durationMs;

    private String name;

    private long popularity;

    private int trackNumber;

    private String type;
}
