package com.musicpedia.musicpediaapi.domain.comment.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentUpdateRequest {
    @Schema(example = "14")
    @NotNull
    private Long commentId;

    @Schema(example = "이번 앨범 나쁘지 않네요")
    @NotBlank
    private String content;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    @NotBlank
    private String spotifyId;

    @Schema(example = "4.5")
    @NotBlank
    private String score;
}