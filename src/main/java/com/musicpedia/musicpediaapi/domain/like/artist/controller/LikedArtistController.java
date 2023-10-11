package com.musicpedia.musicpediaapi.domain.like.artist.controller;

import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistDetail;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistPage;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.request.LikedArtistCreateRequest;
import com.musicpedia.musicpediaapi.domain.like.artist.service.LikedArtistService;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
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
            summary = "아티스트 좋아요 혹은 취소 처리",
            description = "사용자가 아티스트에 대해 좋아요 혹은 좋아요를 취소합니다.",
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
    public ResponseEntity<LikedArtistDetail> likeArtist(@RequestBody LikedArtistCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(likedArtistService.likeArtist(memberId, request));
    }

    @DeleteMapping("/{spotifyId}")
    public ResponseEntity<String> deleteLikedArtist(@PathVariable("spotifyId") String spotifyId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        likedArtistService.deleteArtist(memberId, spotifyId);
        return ResponseEntity.ok("좋아하는 아티스트 삭제 성공");
    }

    @GetMapping()
    public ResponseEntity<LikedArtistPage> getLikedArtists(
            @Parameter(hidden = true) @PageableDefault(size=20, sort="updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());

        return ResponseEntity.ok(likedArtistService.getLikedArtist(memberId, pageable));
    }
}
