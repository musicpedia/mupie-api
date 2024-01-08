package com.musicpedia.musicpediaapi.domain.comment.comment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentPage {
    private List<CommentDetail> comments;

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

    public static CommentPage from(Page<Comment> comments) {
        List<CommentDetail> commentDetails = comments.getContent().stream()
                .map(Comment::toCommentDetail)
                .toList();

        return CommentPage.builder()
                .comments(commentDetails)
                .totalPages(comments.getTotalPages())
                .totalCount(comments.getTotalElements())
                .page(comments.getNumber())
                .size(comments.getSize())
                .hasNext(comments.hasNext())
                .hasPrevious(comments.hasPrevious())
                .isFirstPage(comments.isFirst())
                .isLastPage(comments.isLast())
                .build();
    }
}
