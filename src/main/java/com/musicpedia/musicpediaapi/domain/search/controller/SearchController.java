package com.musicpedia.musicpediaapi.domain.search.controller;

import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTrack;
import com.musicpedia.musicpediaapi.domain.search.service.SearchService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "search")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "전체(앨범, 아티스트, 트랙) 검색", description = "Spotify의 모든 앨범, 아티스트, 트랙에서 검색합니다.")
    @Parameter(name = "keyword", description = "검색어", example = "사랑은 타이밍")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "검색 조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchAlbumTrackArtist.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping
    public ResponseEntity<SpotifySearchAlbumTrackArtist> searchAll(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbumTrackArtist searchInfo = searchService.getAllSearchInfo(memberId, keyword, offset, limit);
        return ResponseEntity.ok(searchInfo);
    }

    @Operation(summary = "앨범 검색", description = "Spotify의 모든 앨범에서 검색합니다.")
    @Parameter(name = "keyword", description = "검색어", example = "만추")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "검색 조회 결과 개수(최대 50)", example = "20")
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
    @GetMapping("/albums")
    public ResponseEntity<SpotifySearchAlbum> searchAlbums(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbum searchInfo = searchService.getAlbumSearchInfo(memberId, keyword, offset, limit);
        return ResponseEntity.ok(searchInfo);
    }

    @Operation(summary = "아티스트 검색", description = "Spotify의 모든 아티스트에서 검색합니다.")
    @Parameter(name = "keyword", description = "검색어", example = "헤이즈")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "검색 조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchArtist.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @GetMapping("/artists")
    public ResponseEntity<SpotifySearchArtist> searchArtists(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchArtist searchInfo = searchService.getArtistSearchInfo(memberId, keyword, offset, limit);
        return ResponseEntity.ok(searchInfo);
    }

    @Operation(summary = "트랙 검색", description = "Spotify의 모든 트랙에서 검색합니다.")
    @Parameter(name = "keyword", description = "검색어", example = "떨어지는 낙엽까지도")
    @Parameter(name = "offset", description = "현재 조회한 결과 위치(0부터 시작)", example = "0")
    @Parameter(name = "limit", description = "검색 조회 결과 개수(최대 50)", example = "20")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = SpotifySearchTrack.class))),
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
    public ResponseEntity<SpotifySearchTrack> searchTracks(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchTrack searchInfo = searchService.getTrackSearchInfo(memberId, keyword, offset, limit);
        return ResponseEntity.ok(searchInfo);
    }
}
