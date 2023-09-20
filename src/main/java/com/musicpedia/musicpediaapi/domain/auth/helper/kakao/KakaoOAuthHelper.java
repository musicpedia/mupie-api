package com.musicpedia.musicpediaapi.domain.auth.helper.kakao;

import com.musicpedia.musicpediaapi.domain.auth.client.KakaoOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;
    private final KakaoOAuthClient kakaoOAuthClient;

    @Value("${oauth.kakao.url.auth}")
    private String iss;

    @Value("${oauth.kakao.client-id}")
    private String aud;

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        // key 찾기
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOAuthClient.getKakaoOIDCOpenKeys();

        return oauthOIDCHelper.getPayloadFromIdToken(
                token,
                iss,
                aud,
                oidcPublicKeysResponse);
    }
}
