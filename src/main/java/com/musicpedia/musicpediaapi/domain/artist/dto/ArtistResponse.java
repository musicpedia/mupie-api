package com.musicpedia.musicpediaapi.domain.artist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArtistResponse {
    private SpotifyArtist spotifyArtist;
    private SpotifySearchAlbum spotifyArtistAlbums;
    private SpotifySearchAlbum spotifyArtistSingles;
    private SpotifySearchAlbum spotifyArtistCompilations;
    private SpotifySearchAlbum spotifyArtistAppearsOn;
    private RelatedArtists relatedArtists;

    private boolean like;
}
