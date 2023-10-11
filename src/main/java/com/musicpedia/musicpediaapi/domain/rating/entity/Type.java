package com.musicpedia.musicpediaapi.domain.rating.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
    @JsonProperty("album")
    ALBUM,

    @JsonProperty("track")
    TRACK
}
