package com.musicpedia.musicpediaapi.domain.track.dto;

import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpotifyTrackInfo {
    private String id;

    private SpotifyAlbumInfo album;

    private List<TrackArtist> artists;

    private long duration_ms;

    private String name;

    private long popularity;

    private int track_number;

    private String type;

    @Data
    public static class TrackArtist {
        private String id;

        private String name;

        private String type;
    }
}
