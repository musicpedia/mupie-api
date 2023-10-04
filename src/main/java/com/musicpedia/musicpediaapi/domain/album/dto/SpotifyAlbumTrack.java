package com.musicpedia.musicpediaapi.domain.album.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAlbumTrack {
    private String id;

    private List<AlbumArtist> artists;

    private long durationMs;

    private String name;

    private int trackNumber;

    private String type;

    @Data
    public static class AlbumArtist {
        private String id;

        private String name;

        private String type;
    }
}