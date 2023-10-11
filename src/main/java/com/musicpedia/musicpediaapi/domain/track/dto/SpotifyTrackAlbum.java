package com.musicpedia.musicpediaapi.domain.track.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyTrackAlbum {
    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String id;

    private List<AlbumImage> images;

    private String name;

    @Schema(example = "album")
    private String type;

    @Schema(example = "single")
    private String albumType;

    private List<AlbumArtist> artists;

    @Schema(example = "2022-10-06")
    private String releaseDate;

    @Schema(example = "day")
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