package com.musicpedia.musicpediaapi.domain.artist.artist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RelatedArtists {
    private List<SpotifyArtist> artists;
}
