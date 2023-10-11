package com.musicpedia.musicpediaapi.domain.album.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyRequestTrack {
    private List<SpotifyAlbumTrack> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}