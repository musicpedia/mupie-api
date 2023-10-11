// SpotifyArtistInfo
package com.musicpedia.musicpediaapi.domain.artist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SpotifyArtist {
    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String id;

    private List<String> genres;

    private List<ArtistImage> images;

    private String name;

    @Schema(example = "artist")
    private String type;

    @Schema(example = "56")
    private int popularity;

    @Data
    public static class ArtistImage {
        private Long height;

        private String url;

        private Long width;
    }
}
