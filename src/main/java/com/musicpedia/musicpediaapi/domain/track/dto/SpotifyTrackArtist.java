package com.musicpedia.musicpediaapi.domain.track.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SpotifyTrackArtist {
    private String id;

    private List<String> genres;

    private List<ArtistImage> images;

    private String name;

    @Schema(example = "artist")
    private String type;

    @Schema(example = "57")
    private int popularity;

    @Data
    public static class ArtistImage {
        private Long height;

        private String url;

        private Long width;
    }
}