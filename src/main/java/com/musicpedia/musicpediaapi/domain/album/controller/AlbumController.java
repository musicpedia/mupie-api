package com.musicpedia.musicpediaapi.domain.album.controller;

import com.musicpedia.musicpediaapi.domain.album.dto.AlbumResponse;
import com.musicpedia.musicpediaapi.domain.album.dto.AlbumWithTracks;
import com.musicpedia.musicpediaapi.domain.album.service.AlbumService;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "album")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/album")
public class AlbumController {
    private final AlbumService albumService;
    private final RatingService ratingService;

    @Operation(summary = "앨범 정보 조회", description = "Spotify에서 id에 해당하는 앨범 정보를 조회합니다.")
    @Parameter(name = "albumId", description = "spotify의 앨범 id", example = "1tfAfSTJHXtmgkzDwBasOp", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumResponse> getAlbum(@PathVariable String albumId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        AlbumWithTracks albumInfo = albumService.getAlbum(memberId, albumId);
        Score score = ratingService.getScore(memberId, albumId);
        AlbumResponse albumResponse = AlbumResponse.builder()
                .album(albumInfo)
                .score(score)
                .build();
        return ResponseEntity.ok(albumResponse);
    }
}
