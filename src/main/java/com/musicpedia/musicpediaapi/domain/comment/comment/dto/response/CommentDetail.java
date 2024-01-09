package com.musicpedia.musicpediaapi.domain.comment.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.member.dto.response.MemberDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDetail {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "이번 앨범 좋네요")
    private String content;

    @Schema(example = "1tfAfSTJHXtmgkzDwBasOp")
    private String spotifyId;

    @Schema(example = "17")
    private Long likeCount;

    private Writer writer;

    @Schema(example = "true")
    private boolean isModified;

    @Schema(example = "2023-10-12 01:45:14.291106")
    private LocalDateTime createdAt;

    @Data
    @Builder
    public static class Writer {
        @Schema(example = "22")
        private Long id;

        @Schema(example = "곽의준")
        private String name;

        @Schema(example = "https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
        private String profileImage;

        @Schema(example = "4.5")
        private String score;
    }
}
