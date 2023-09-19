package com.musicpedia.musicpediaapi.domain.auth.client;

import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {
    private final RestTemplate restTemplate;

    public OIDCPublicKeysResponse kakaoAuth(String clientId, String redirectUri, String code, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<OIDCPublicKeysResponse> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                requestEntity,
                OIDCPublicKeysResponse.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // 에러 처리
            throw new RuntimeException("KakaoAuth request failed");
        }
    }

    @Cacheable(cacheNames = "KakaoOICD", cacheManager = "oidcCacheManager")
    public OIDCPublicKeysResponse getKakaoOIDCOpenKeys() {
        ResponseEntity<OIDCPublicKeysResponse> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/.well-known/jwks.json",
                HttpMethod.GET,
                null,
                OIDCPublicKeysResponse.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // 에러 처리
            throw new RuntimeException("KakaoOIDCOpenKeys request failed");
        }
    }
}
