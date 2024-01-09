package com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyCommentCreateRequest {
    @Schema(example = "14")
    @NotNull
    private Long commentId;

    @Schema(example = "좋은 감상평이네요.")
    @NotBlank
    private String content;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    @NotBlank
    private String spotifyId;

    @Schema(example = "4.5")
    @NotBlank
    private String score;

    public ReplyComment toReplyComment() {
        return ReplyComment.builder()
                .content(content)
                .spotifyId(spotifyId)
                .score(score)
                .build();
    }
}
