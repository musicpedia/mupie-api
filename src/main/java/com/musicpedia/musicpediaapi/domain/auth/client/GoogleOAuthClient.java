package com.musicpedia.musicpediaapi.domain.auth.client;

import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleOAuthClient implements OAuthClient{
    private final RestTemplate restTemplate;

    @Override
    @Cacheable(cacheNames = "GoogleOIDC", cacheManager = "oidcCacheManager")
    public OIDCPublicKeysResponse getOIDCOpenKeys() {
        ResponseEntity<OIDCPublicKeysResponse> responseEntity = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/certs",
                HttpMethod.GET,
                null,
                OIDCPublicKeysResponse.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // 에러 처리
            throw new RuntimeException("GoogleOIDCOpenKeys request failed");
        }
    }
}
