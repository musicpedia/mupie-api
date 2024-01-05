package com.musicpedia.musicpediaapi.domain.album.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAlbumWithTracks {
    private String id;

    private List<AlbumImage> images;

    private String name;

    private String type;

    private String albumType;

    private List<AlbumArtist> artists;

    private String releaseDate;

    private String releaseDatePrecision;

    private int totalTracks;

    private int popularity;

    private List<SpotifyAlbumTrack> trackList;

    public AlbumWithTracks toAlbumWithTracks(List<AlbumTrack> albumTracks) {
        return AlbumWithTracks.builder()
                .id(id)
                .images(images)
                .name(name)
                .type(type)
                .albumType(albumType)
                .artists(artists)
                .releaseDate(releaseDate)
                .releaseDatePrecision(releaseDatePrecision)
                .totalTracks(totalTracks)
                .popularity(popularity)
                .trackList(albumTracks)
                .build();
    }
}
