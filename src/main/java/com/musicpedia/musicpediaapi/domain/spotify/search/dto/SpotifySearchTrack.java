package com.musicpedia.musicpediaapi.domain.spotify.search.dto;

import com.musicpedia.musicpediaapi.domain.spotify.track.dto.SpotifyTrack;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchTrack {
    private List<SpotifyTrack> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}