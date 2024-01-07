package com.musicpedia.musicpediaapi.domain.artist.artist.controller;

import com.musicpedia.musicpediaapi.domain.artist.artist.dto.RelatedArtists;
import com.musicpedia.musicpediaapi.domain.artist.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.artist.artist.service.ArtistService;
import com.musicpedia.musicpediaapi.domain.artist.artist.dto.ArtistResponse;
import com.musicpedia.musicpediaapi.domain.artist.liked_artist.service.LikedArtistService;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "artist")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/artist")
public class ArtistController {
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_OFFSET = 0;
    private final ArtistService artistService;
    private final LikedArtistService likedArtistService;

    @Operation(summary = "아티스트, 아티스트 앨범 정보 조회", description = "Spotify에서 id에 해당하는 아티스트 정보와 아티스트의 싱글, 앨범, 컴필레이션, 참여 앨범를 조회합니다. 기본 제공 데이터 수는 10개 입니다.")
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
        SpotifySearchAlbum artistAlbums = artistService.getArtistAlbums(memberId, artistId, DEFAULT_OFFSET, DEFAULT_LIMIT);
        SpotifySearchAlbum artistSingles = artistService.getArtistSingles(memberId, artistId, DEFAULT_OFFSET, DEFAULT_LIMIT);
        SpotifySearchAlbum artistCompilations = artistService.getArtistCompilations(memberId, artistId, DEFAULT_OFFSET, DEFAULT_LIMIT);
        SpotifySearchAlbum artistAppearsOn = artistService.getArtistAppearsOn(memberId, artistId, DEFAULT_OFFSET, DEFAULT_LIMIT);
        RelatedArtists relatedArtists = artistService.getRelatedArtists(memberId, artistId);

        boolean like = likedArtistService.isMemberLike(memberId, artistId);
        ArtistResponse artistResponse = ArtistResponse.builder()
                .spotifyArtist(spotifyArtist)
                .spotifyArtistAlbums(artistAlbums)
                .spotifyArtistSingles(artistSingles)
                .spotifyArtistCompilations(artistCompilations)
                .spotifyArtistAppearsOn(artistAppearsOn)
                .relatedArtists(relatedArtists)
                .like(like)
                .build();

        return ResponseEntity.ok(artistResponse);
    }

    @Operation(summary = "아티스트의 정규 앨범 정보 조회", description = "아티스트의 정규 앨범 정보를 조회합니다.")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchAlbum.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}/albums")
    public ResponseEntity<SpotifySearchAlbum> getArtistAlbums(
            @PathVariable String artistId,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbum artistAlbums = artistService.getArtistAlbums(memberId, artistId, offset, limit);

        return ResponseEntity.ok(artistAlbums);
    }

    @Operation(summary = "아티스트의 싱글 정보 조회", description = "아티스트의 싱글 정보를 조회합니다.")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchAlbum.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}/singles")
    public ResponseEntity<SpotifySearchAlbum> getArtistSingles(
            @PathVariable String artistId,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbum artistSingles = artistService.getArtistSingles(memberId, artistId, offset, limit);

        return ResponseEntity.ok(artistSingles);
    }

    @Operation(summary = "아티스트의 컴필 앨범 정보 조회", description = "아티스트의 컴필 앨범 정보를 조회합니다.")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchAlbum.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}/compilations")
    public ResponseEntity<SpotifySearchAlbum> getArtistCompilations(
            @PathVariable String artistId,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbum artistCompilations = artistService.getArtistCompilations(memberId, artistId, offset, limit);

        return ResponseEntity.ok(artistCompilations);
    }

    @Operation(summary = "아티스트의 참여 앨범 조회", description = "아티스트의 참여 앨범 정보를 조회합니다.")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchAlbum.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}/appears-on")
    public ResponseEntity<SpotifySearchAlbum> getArtistAppearsOn(
            @PathVariable String artistId,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbum artistAppearsOn = artistService.getArtistAppearsOn(memberId, artistId, offset, limit);

        return ResponseEntity.ok(artistAppearsOn);
    }

    @Operation(summary = "관련 아티스트 조회", description = "아티스트와 관련된 아티스트를 조회합니다.")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = RelatedArtists.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/{artistId}/related-artists")
    public ResponseEntity<RelatedArtists> getRelatedArtists(
            @PathVariable String artistId,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        RelatedArtists relatedArtists = artistService.getRelatedArtists(memberId, artistId);

        return ResponseEntity.ok(relatedArtists);
    }
}