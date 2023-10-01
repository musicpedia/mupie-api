package com.musicpedia.musicpediaapi.domain.spotify.search.dto;

import lombok.Data;

@Data
public class SpotifySearchAlbumTrackArtist {
    private SpotifySearchAlbum albums;

    private SpotifySearchArtist artists;

    private SpotifySearchTrack tracks;
}
