package com.musicpedia.musicpediaapi.domain.rating.controller;

import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingUpdateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping()
    public ResponseEntity<RatingDetail> saveRating(@RequestBody RatingCreateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRating(memberId, request));
    }

    @PutMapping()
    public ResponseEntity<String> updateRating(@RequestBody RatingUpdateRequest request, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        ratingService.updateRating(memberId, request);
        return ResponseEntity.ok("평점 수정 성공");
    }

    @DeleteMapping("/{spotify_id}")
    public ResponseEntity<String> deleteRating(@PathVariable("spotify_id") String spotifyId, HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        ratingService.deleteRating(memberId, spotifyId);
        return ResponseEntity.ok("평점 삭제 성공");
    }
}
