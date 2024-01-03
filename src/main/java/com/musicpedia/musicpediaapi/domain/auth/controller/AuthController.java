package com.musicpedia.musicpediaapi.domain.auth.controller;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.service.AuthService;
import com.musicpedia.musicpediaapi.domain.auth.service.apple.AppleOAuthLoginService;
import com.musicpedia.musicpediaapi.domain.auth.service.google.GoogleOAuthLoginService;
import com.musicpedia.musicpediaapi.domain.auth.service.kakao.KakaoOAuthLoginService;
import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final KakaoOAuthLoginService kakaoOAuthLoginService;
    private final GoogleOAuthLoginService googleOAuthLoginService;
    private final AppleOAuthLoginService appleOAuthLoginService;
    private final AuthService authService;

    @Operation(
            summary = "카카오 로그인",
            description = "카카오의 Id Token으로 카카오 로그인을 진행합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "카카오의 Id Token",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AuthTokens.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody @Valid OAuthLoginParams loginParams) {
        return ResponseEntity.ok(kakaoOAuthLoginService.login(loginParams));
    }

    @Operation(
            summary = "구글 로그인",
            description = "구글의 Id Token으로 구글 로그인을 진행합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "구글의 Id Token",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AuthTokens.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping("/login/google")
    public ResponseEntity<AuthTokens> loginGoogle(@RequestBody @Valid OAuthLoginParams loginParams) {
        return ResponseEntity.ok(googleOAuthLoginService.login(loginParams));
    }

    @Operation(
            summary = "애플 로그인",
            description = "애플의 Id Token으로 애플 로그인을 진행합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "애플의 Id Token",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AuthTokens.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping("/login/apple")
    public ResponseEntity<AuthTokens> loginApple(@RequestBody @Valid OAuthLoginParams loginParams) {
        return ResponseEntity.ok(appleOAuthLoginService.login(loginParams));
    }

    @Operation(
            summary = "토큰 재발급",
            description = "Access Token과 Refresh Token을 검증하여 Token을 재발급합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JWT 토큰(Access Token, Refresh Token)과 Grant Type",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AuthTokens.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping("/reissue")
    public ResponseEntity<AuthTokens> reissue(@RequestBody @Valid AuthTokens authTokens) {
        return ResponseEntity.ok(authService.reissueTokens(authTokens));
    }

    @Operation(
            summary = "게스트 로그인",
            description = "게스트의 Access Token과 Refresh Token을 발급합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AuthTokens.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(type = "string")))
    })
    @PostMapping("/login/guest")
    public ResponseEntity<AuthTokens> loginGuest() {
        return ResponseEntity.ok(authService.guestLogin());
    }

    @GetMapping("/version")
    public ResponseEntity<String> getAppVersion() {
        return ResponseEntity.ok(authService.getMinimumCompatibleVersion());
    }
}