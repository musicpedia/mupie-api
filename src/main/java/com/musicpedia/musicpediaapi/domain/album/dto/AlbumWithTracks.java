package com.musicpedia.musicpediaapi.domain.album.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlbumWithTracks {
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

    private List<AlbumTrack> trackList;
}