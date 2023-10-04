package com.musicpedia.musicpediaapi.domain.search.controller;

import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTrack;
import com.musicpedia.musicpediaapi.domain.search.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {
    private final SearchService searchService;

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
