package com.musicpedia.musicpediaapi.domain.auth.helper.apple;

import com.musicpedia.musicpediaapi.domain.auth.client.AppleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleOAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;
    private final AppleOAuthClient appleOAuthClient;

    @Value("${oauth.apple.url.auth}")
    private String iss;

    @Value("${oauth.apple.client-id}")
    private String aud;

    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        // key 찾기
        OIDCPublicKeysResponse oidcPublicKeysResponse = appleOAuthClient.getAppleOIDCOpenKeys();
        return oauthOIDCHelper.getPayloadFromIdToken(
                token,
                iss,
                aud,
                oidcPublicKeysResponse
        );
    }
}
