package com.musicpedia.musicpediaapi.domain.spotify.search.dto;

import com.musicpedia.musicpediaapi.domain.spotify.track.dto.SpotifyTrackInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpotifySearchTracksInfo {
    private List<SpotifyTrackInfo> items;

    private int limit;

    private long offset;

    private long total;

    private String previous;

    private String next;
}