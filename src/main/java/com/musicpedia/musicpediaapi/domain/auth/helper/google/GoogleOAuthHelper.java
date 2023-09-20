package com.musicpedia.musicpediaapi.domain.auth.helper.google;

import com.musicpedia.musicpediaapi.domain.auth.client.GoogleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.client.KakaoOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;
    private final GoogleOAuthClient googleOAuthClient;

    @Value("${oauth.google.url.auth}")
    private String iss;

    @Value("${oauth.google.client-id}")
    private String aud;

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        // key 찾기
        OIDCPublicKeysResponse oidcPublicKeysResponse = googleOAuthClient.getGoogleOIDCOpenKeys();
        System.out.println("oidcPublicKeysResponse = " + oidcPublicKeysResponse.getKeys().get(0).getKid());
        return oauthOIDCHelper.getPayloadFromIdToken(
                token,
                iss,
                aud,
                oidcPublicKeysResponse
        );
    }
}
