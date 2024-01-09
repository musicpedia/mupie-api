package com.musicpedia.musicpediaapi.domain.comment.reply_comment.controller;

import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentPage;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.service.ReplyCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "reply comment")
@RestController
@RequestMapping("/v1/reply-comment")
@RequiredArgsConstructor
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    @Operation(
            summary = "코멘트 답변 저장",
            description = "코멘트 id에 해당하는 코멘트에 코멘트 답변을 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "comment id, 코멘트 답변 내용",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = ReplyCommentDetail.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping()
    public ResponseEntity<ReplyCommentDetail> saveReplyComment(@RequestBody @Valid ReplyCommentCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(replyCommentService.saveReplyComment(memberId, request));
    }

    @Operation(summary = "코멘트 id 해당하는 코멘트 답변 조회", description = "코멘트 id에 해당하는 코멘트 답변들을 조회합니다.")
    @Parameter(name = "commentId", description = "조회할 코멘트 답변이 있는 코멘트의 id", example = "12", required = true)
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 댓글 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "likeCount,createdAt,score,DESC")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ReplyCommentPage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<ReplyCommentPage> getTrackRatings(
            @PathVariable("commentId") long commentId,
            @Parameter(hidden = true) @PageableDefault(size=20, sort="updatedAt", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.ok(replyCommentService.getReplyComments(memberId, commentId, pageable));
    }

    @Operation(
            summary = "코멘트 답변 수정",
            description = "코멘트 답변 id에 해당하는 코멘트 답변을 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "코멘트 답변 id, spotify id, 수정한 코멘트 내용, 답변자의 평점",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PutMapping()
    public ResponseEntity<String> updateReplyComment(@RequestBody @Valid ReplyCommentUpdateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        replyCommentService.updateReplyComment(memberId, request);

        return ResponseEntity.ok("코멘트 답변 수정 성공");
    }

    @Operation(summary = "코멘트 답변 id에 해당하는 코멘트 삭제", description = "comment id에 해당하는 코멘트를 삭제합니다.")
    @Parameter(name = "replyCommentId", description = "삭제할 코멘트 답변의 id", example = "12", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @DeleteMapping("/{replyCommentId}")
    public ResponseEntity<String> deleteRating(@PathVariable("replyCommentId") Long replyCommentId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        replyCommentService.deleteReplyComment(memberId, replyCommentId);

        return ResponseEntity.ok("코멘트 답변 삭제 성공");
    }
}
