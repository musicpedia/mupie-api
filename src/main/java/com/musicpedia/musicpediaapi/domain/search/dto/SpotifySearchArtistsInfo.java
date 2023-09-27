package com.musicpedia.musicpediaapi.domain.search.dto;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchArtistsInfo {
    private List<SpotifyArtistInfo> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}