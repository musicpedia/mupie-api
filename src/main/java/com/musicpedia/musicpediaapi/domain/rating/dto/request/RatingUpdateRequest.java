package com.musicpedia.musicpediaapi.domain.rating.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingUpdateRequest {
    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String spotifyId;

    @Schema(example = "4.5")
    private String score;
}