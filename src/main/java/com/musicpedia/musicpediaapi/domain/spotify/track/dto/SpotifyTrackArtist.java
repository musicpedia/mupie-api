package com.musicpedia.musicpediaapi.domain.spotify.track.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyTrackArtist {
    private String id;

    private List<String> genres;

    private List<ArtistImage> images;

    private String name;

    private String type;

    private String popularity;

    @Data
    public static class ArtistImage {
        private Long height;

        private String url;

        private Long width;
    }
}