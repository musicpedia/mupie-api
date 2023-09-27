package com.musicpedia.musicpediaapi.domain.track.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumInfo;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyTrackInfo {
    private String id;

    private SpotifyAlbumInfo album;

    private List<TrackArtist> artists;

    private long durationMs;

    private String name;

    private long popularity;

    private int trackNumber;

    private String type;

    @Data
    public static class TrackArtist {
        private String id;

        private String name;

        private String type;
    }
}
