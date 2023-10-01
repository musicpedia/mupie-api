package com.musicpedia.musicpediaapi.domain.spotify.track.controller;

import com.musicpedia.musicpediaapi.domain.spotify.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.spotify.track.dto.SpotifyTrack;
import com.musicpedia.musicpediaapi.domain.spotify.track.service.TrackService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/track")
public class TrackController {
    private final TrackService trackService;

    @GetMapping("/{trackId}")
    public ResponseEntity<SpotifyTrack> getTrack(@PathVariable String trackId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifyTrack track = trackService.getTrack(memberId, trackId);
        return ResponseEntity.ok(track);
    }
}
