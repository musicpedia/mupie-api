package com.musicpedia.musicpediaapi.domain.auth.controller;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.service.AuthService;
import com.musicpedia.musicpediaapi.domain.auth.service.apple.AppleOAuthLoginService;
import com.musicpedia.musicpediaapi.domain.auth.service.google.GoogleOAuthLoginService;
import com.musicpedia.musicpediaapi.domain.auth.service.kakao.KakaoOAuthLoginService;
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
    private final GoogleOAuthLoginService googleOAuthLoginService;
    private final AppleOAuthLoginService appleOAuthLoginService;
    private final AuthService authService;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody OAuthLoginParams loginParams) {
        return ResponseEntity.ok(kakaoOAuthLoginService.login(loginParams));
    }

    @PostMapping("/login/google")
    public ResponseEntity<AuthTokens> loginGoogle(@RequestBody OAuthLoginParams loginParams) {
        return ResponseEntity.ok(googleOAuthLoginService.login(loginParams));
    }

    @PostMapping("/login/apple")
    public ResponseEntity<AuthTokens> loginApple(@RequestBody OAuthLoginParams loginParams) {
        return ResponseEntity.ok(appleOAuthLoginService.login(loginParams));
    }

    @PostMapping("/reissue")
    public ResponseEntity<AuthTokens> reissue(@RequestBody AuthTokens authTokens) {
        return ResponseEntity.ok(authService.reissueTokens(authTokens));
    }
}