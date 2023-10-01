package com.musicpedia.musicpediaapi.domain.spotify.album.controller;

import com.musicpedia.musicpediaapi.domain.spotify.album.dto.SpotifyAlbumWithTracks;
import com.musicpedia.musicpediaapi.domain.spotify.album.service.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/album")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/{albumId}")
    public ResponseEntity<SpotifyAlbumWithTracks> getAlbum(@PathVariable String albumId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        SpotifyAlbumWithTracks albumInfo = albumService.getAlbum(memberId, albumId);
        return ResponseEntity.ok(albumInfo);
    }
}
