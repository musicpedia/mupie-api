package com.musicpedia.musicpediaapi.domain.like.artist.controller;

import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistDetail;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistPage;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.request.LikedArtistCreateRequest;
import com.musicpedia.musicpediaapi.domain.like.artist.service.LikedArtistService;
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

@Tag(name = "like")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/artist/like")
public class LikedArtistController {
    private final LikedArtistService likedArtistService;

    @Operation(
            summary = "좋아하는 아티스트 저장",
            description = "사용자가 좋아하는 아티스트를 저장합니다",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "아티스트 기본 정보 (이름, spotify id, 썸네일)",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = LikedArtistDetail.class))),
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
    public ResponseEntity<LikedArtistDetail> likeArtist(@RequestBody LikedArtistCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(likedArtistService.likeArtist(memberId, request));
    }

    @Operation(summary = "좋아하는 아티스트 삭제", description = "사용자가 좋아하는 아티스트를 삭제합니다")
    @Parameter(name = "spotifyId", description = "좋아하는 아티스트의 spotify id", example = "0TnOYISbd1XYRBk9myaseg", required = true)
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
    public ResponseEntity<String> deleteLikedArtist(@PathVariable("spotifyId") String spotifyId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        likedArtistService.deleteArtist(memberId, spotifyId);
        return ResponseEntity.ok("좋아하는 아티스트 삭제 성공");
    }

    @Operation(summary = "사용자가 좋아하는 아티스트 조회", description = "사용자가 좋아하는 아티스트를 조회합니다.")
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 평점 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "updatedAt,name,DESC")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = LikedArtistPage.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping()
    public ResponseEntity<LikedArtistPage> getLikedArtists(
            @Parameter(hidden = true) @PageableDefault(size=20, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.ok(likedArtistService.getLikedArtists(memberId, pageable));
    }
}
