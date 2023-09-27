package com.musicpedia.musicpediaapi.domain.album.dto;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpotifyAlbumInfo {
    private String id;

    private List<SpotifyArtistInfo.ArtistImage> images;

    private String name;

    private String type;

    private String album_type;

    private List<AlbumArtist> artists;

    private String release_date;

    private String release_date_precision;

    private int total_tracks;

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
