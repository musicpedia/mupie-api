package com.musicpedia.musicpediaapi.domain.search.controller;

import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtistInfo;
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
    public ResponseEntity<SpotifySearchAlbumTrackArtistInfo> searchAll(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "offset") long offset,
            @RequestParam(name = "limit") int limit,
            HttpServletRequest httpServletRequest
    ) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifySearchAlbumTrackArtistInfo searchInfo = searchService.getAllSearchInfo(memberId, keyword, offset, limit);
        return ResponseEntity.ok(searchInfo);
    }
}
