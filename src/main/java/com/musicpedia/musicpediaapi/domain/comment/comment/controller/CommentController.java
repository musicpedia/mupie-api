package com.musicpedia.musicpediaapi.domain.comment.comment.controller;

import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "comment")
@RestController
@RequestMapping("/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "코멘트 저장",
            description = "spotify id에 해당하는 항목의 코멘트를 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "spotify id, 코멘트 내용",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = CommentDetail.class))),
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
    public ResponseEntity<CommentDetail> saveComment(@RequestBody @Valid CommentCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(memberId, request));
    }
}
