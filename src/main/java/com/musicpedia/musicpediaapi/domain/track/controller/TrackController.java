package com.musicpedia.musicpediaapi.domain.track.controller;

import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import com.musicpedia.musicpediaapi.domain.track.dto.TrackResponse;
import com.musicpedia.musicpediaapi.domain.track.service.TrackService;
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

@Tag(name = "track")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/track")
public class TrackController {
    private final TrackService trackService;
    private final RatingService ratingService;

    @Operation(summary = "트랙 정보 조회", description = "Spotify에서 id에 해당하는 트랙 정보를 조회합니다.")
    @Parameter(name = "trackId", description = "spotify의 트랙 id", example = "11dFghVXANMlKmJXsNCbNl", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = TrackResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{trackId}")
    public ResponseEntity<TrackResponse> getTrack(@PathVariable String trackId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifyTrack track = trackService.getTrack(memberId, trackId);
        Score score = ratingService.getScore(memberId, trackId);
        TrackResponse trackResponse = TrackResponse.builder()
                .spotifyTrack(track)
                .score(score)
                .build();
        return ResponseEntity.ok(trackResponse);
    }
}
