package com.musicpedia.musicpediaapi.domain.search.dto;

import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbum;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchAlbum {
    private List<SpotifyAlbum> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}
