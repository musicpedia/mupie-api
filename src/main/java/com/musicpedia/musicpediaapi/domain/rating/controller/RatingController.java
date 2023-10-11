package com.musicpedia.musicpediaapi.domain.rating.controller;

import com.musicpedia.musicpediaapi.domain.member.dto.MemberDetail;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingUpdateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingPage;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "rating")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @Operation(
            summary = "앨범, 트랙 평점 저장",
            description = "사용자가 평가한 항목(앨범, 트랙)을 저장합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "평점 및 평가 항목의 기본 정보",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = RatingDetail.class))),
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
    public ResponseEntity<RatingDetail> saveRating(@RequestBody RatingCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRating(memberId, request));
    }

    @Operation(
            summary = "앨범, 트랙 평점 수정",
            description = "사용자가 평가한 항목(앨범, 트랙)을 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "해당 항목의 spotify id와 수정한 평점",
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
    public ResponseEntity<String> updateRating(@RequestBody RatingUpdateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        ratingService.updateRating(memberId, request);
        return ResponseEntity.ok("평점 수정 성공");
    }

    @Operation(summary = "앨범, 트랙 평점 삭제", description = "사용자가 평가한 항목(앨범, 트랙)을 삭제합니다.")
    @Parameter(name = "spotifyId", description = "평가한 항목의 spotify id", example = "1tfAfSTJHXtmgkzDwBasOp", required = true)
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
    @DeleteMapping("/{spotifyId}")
    public ResponseEntity<String> deleteRating(@PathVariable("spotifyId") String spotifyId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        ratingService.deleteRating(memberId, spotifyId);
        return ResponseEntity.ok("평점 삭제 성공");
    }

    @Operation(summary = "사용자의 앨범 평점 조회", description = "사용자가 평가한 앨범 평점을 조회합니다.")
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 평점 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "updatedAt,score,DESC")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = RatingPage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/albums")
    public ResponseEntity<RatingPage> getAlbumRatings(
            @Parameter(hidden = true) @PageableDefault(size=20, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.ok(ratingService.getRatings(memberId, Type.ALBUM, pageable));
    }

    @Operation(summary = "사용자의 트랙 평점 조회", description = "사용자가 평가한 트랙 평점을 조회합니다.")
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 평점 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "updatedAt,score,DESC")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = RatingPage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/tracks")
    public ResponseEntity<RatingPage> getTrackRatings(
            @Parameter(hidden = true) @PageableDefault(size=20, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.ok(ratingService.getRatings(memberId, Type.TRACK, pageable));
    }
}
