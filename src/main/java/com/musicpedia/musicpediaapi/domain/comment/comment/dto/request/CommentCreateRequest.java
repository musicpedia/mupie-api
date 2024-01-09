package com.musicpedia.musicpediaapi.domain.comment.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentCreateRequest {
    @Schema(example = "이번 앨범 좋네요")
    @NotBlank
    private String content;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    @NotBlank
    private String spotifyId;

    @Schema(example = "4.5")
    @NotBlank
    private String score;

    public Comment toComment() {
        return Comment.builder()
                .content(content)
                .spotifyId(spotifyId)
                .score(score)
                .build();
    }
}
