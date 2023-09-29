package com.musicpedia.musicpediaapi.domain.search.dto;

import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchAlbumsInfo {
    private List<SpotifyAlbumInfo> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}
