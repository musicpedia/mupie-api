package com.musicpedia.musicpediaapi.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthLoginParams {
    @JsonProperty("id_token")
    private String idToken;
}
