package com.musicpedia.musicpediaapi.domain.search.dto;

import lombok.Data;

@Data
public class SpotifySearchAlbumTrackArtist {
    private SpotifySearchAlbum albums;

    private SpotifySearchArtist artists;

    private SpotifySearchTrack tracks;
}
