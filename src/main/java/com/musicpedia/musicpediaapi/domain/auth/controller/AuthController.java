package com.musicpedia.musicpediaapi.domain.auth.controller;

import com.musicpedia.musicpediaapi.domain.auth.dto.kakao.KakaoLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.service.AuthService;
import com.musicpedia.musicpediaapi.domain.auth.service.KakaoOAuthLoginService;
import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final KakaoOAuthLoginService kakaoOAuthLoginService;
    private final AuthService authService;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams kakaoLoginParams) {
        return ResponseEntity.ok(kakaoOAuthLoginService.login(kakaoLoginParams));
    }

    @PostMapping("/reissue")
    public ResponseEntity<AuthTokens> reissue(@RequestBody AuthTokens authTokens) {
        return ResponseEntity.ok(authService.reissueTokens(authTokens));
    }
}