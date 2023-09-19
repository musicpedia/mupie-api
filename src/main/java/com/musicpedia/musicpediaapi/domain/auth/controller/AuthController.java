package com.musicpedia.musicpediaapi.domain.auth.controller;

import com.musicpedia.musicpediaapi.domain.auth.dto.kakao.KakaoLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.service.KakaoOAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KakaoOAuthLoginService kakaoOAuthLoginService;

    @PostMapping("/login/kakao")
    public ResponseEntity<Long> loginKakao(@RequestBody KakaoLoginParams kakaoLoginParams) {
        Long memberId = kakaoOAuthLoginService.login(kakaoLoginParams);
        return ResponseEntity.ok(memberId);
    }
}