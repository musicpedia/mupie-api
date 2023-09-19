package com.musicpedia.musicpediaapi.domain.auth.controller;

import com.musicpedia.musicpediaapi.domain.auth.dto.kakao.KakaoLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.service.OAuthLoginService;
import com.musicpedia.musicpediaapi.global.util.AuthTokens;
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
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}