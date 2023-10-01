package com.musicpedia.musicpediaapi.domain.spotify.track.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyTrackAlbum {
    private String id;

    private List<AlbumImage> images;

    private String name;

    private String type;

    private String albumType;

    private List<AlbumArtist> artists;

    private String releaseDate;

    private String releaseDatePrecision;

    private int totalTracks;

    @Data
    public static class AlbumImage {
        private Long height;

        private String url;

        private Long width;
    }

    @Data
    public static class AlbumArtist {
        private String id;

        private String name;

        private String type;
    }
}