package com.musicpedia.musicpediaapi.domain.artist.controller;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import com.musicpedia.musicpediaapi.domain.artist.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/artist")
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping("/{artistId}")
    public ResponseEntity<SpotifyArtistInfo> getArtist(@PathVariable String artistId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return artistService.getArtistInfo(memberId, artistId);
    }
}