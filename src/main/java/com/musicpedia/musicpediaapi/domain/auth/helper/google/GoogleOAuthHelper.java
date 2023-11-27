package com.musicpedia.musicpediaapi.domain.auth.helper.google;

import com.musicpedia.musicpediaapi.domain.auth.client.GoogleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthHelper extends OAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;
    private final GoogleOAuthClient googleOAuthClient;

    @Value("${oauth.google.url.auth}")
    private String iss;

    @Value("${oauth.google.client-id}")
    private String aud;

    @Override
    protected OIDCPublicKeysResponse getOIDCOpenKeys() {
        return googleOAuthClient.getOIDCOpenKeys();
    }

    @Override
    protected OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        return oauthOIDCHelper.getPayloadFromIdToken(token, iss, aud, oidcPublicKeysResponse);
    }
}
