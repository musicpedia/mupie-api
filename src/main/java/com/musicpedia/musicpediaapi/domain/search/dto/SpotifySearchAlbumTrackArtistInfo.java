package com.musicpedia.musicpediaapi.domain.search.dto;

import lombok.Data;

@Data
public class SpotifySearchAlbumTrackArtistInfo {
    private SpotifySearchAlbumsInfo albums;

    private SpotifySearchArtistsInfo artists;

    private SpotifySearchTracksInfo tracks;
}
