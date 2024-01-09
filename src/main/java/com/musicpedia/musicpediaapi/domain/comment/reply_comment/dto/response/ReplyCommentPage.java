package com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReplyCommentPage {
    private List<ReplyCommentDetail> comments;

    @Schema(example = "13")
    private int totalPages;

    @Schema(example = "183")
    private long totalCount;

    @Schema(example = "2")
    private int page;

    @Schema(example = "20")
    private int size;

    private boolean hasNext;

    private boolean hasPrevious;

    private boolean isFirstPage;

    private boolean isLastPage;

    public static ReplyCommentPage from(Page<ReplyComment> replyComments) {
        List<ReplyCommentDetail> replyCommentDetails = replyComments.getContent().stream()
                .map(ReplyComment::toReplyCommentDetail)
                .toList();

        return ReplyCommentPage.builder()
                .comments(replyCommentDetails)
                .totalPages(replyComments.getTotalPages())
                .totalCount(replyComments.getTotalElements())
                .page(replyComments.getNumber())
                .size(replyComments.getSize())
                .hasNext(replyComments.hasNext())
                .hasPrevious(replyComments.hasPrevious())
                .isFirstPage(replyComments.isFirst())
                .isLastPage(replyComments.isLast())
                .build();
    }
}
