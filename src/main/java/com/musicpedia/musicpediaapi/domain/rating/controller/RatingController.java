package com.musicpedia.musicpediaapi.domain.rating.controller;

import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(ratingService.saveRating(memberId, request));
    }

    @GetMapping()
    public ResponseEntity<String> getMember(HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        ratingService.getRating(memberId);
        return ResponseEntity.ok("wehei");
    }
}
