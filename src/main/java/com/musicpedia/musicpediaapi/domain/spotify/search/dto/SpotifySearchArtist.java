package com.musicpedia.musicpediaapi.domain.spotify.search.dto;

import com.musicpedia.musicpediaapi.domain.spotify.artist.dto.SpotifyArtist;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchArtist {
    private List<SpotifyArtist> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}