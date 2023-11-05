package com.musicpedia.musicpediaapi.domain.artist.controller;

import com.musicpedia.musicpediaapi.domain.artist.dto.ArtistResponse;
import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.artist.service.ArtistService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "artist")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final LikedArtistService likedArtistService;

    @Operation(summary = "아티스트 정보 조회", description = "Spotify에서 id에 해당하는 아티스트 정보를 조회합니다.")
    @Parameter(name = "artistId", description = "spotify의 아티스트 id", example = "0TnOYISbd1XYRBk9myaseg", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArtistResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponse> getArtist(@PathVariable String artistId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifyArtist spotifyArtist = artistService.getArtistInfo(memberId, artistId);
        boolean like = likedArtistService.isMemberLike(memberId, artistId);
        ArtistResponse artistResponse = ArtistResponse.builder()
                .spotifyArtist(spotifyArtist)
                .like(like)
                .build();

        return ResponseEntity.ok(artistResponse);
    }
}
