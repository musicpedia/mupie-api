package com.musicpedia.musicpediaapi.domain.album.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAlbumInfo {
    private String id;

    private List<SpotifyArtistInfo.ArtistImage> images;

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
