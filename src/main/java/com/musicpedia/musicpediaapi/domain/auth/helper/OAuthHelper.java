package com.musicpedia.musicpediaapi.domain.auth.helper;

import com.musicpedia.musicpediaapi.domain.auth.client.OAuthClient;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class OAuthHelper {
    private final OAuthClient oAuthClient;
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcOpenKeys = getOIDCOpenKeys();

        return getPayloadFromIdToken(token, oidcOpenKeys);
    }

    private OIDCPublicKeysResponse getOIDCOpenKeys() {
        return oAuthClient.getOIDCOpenKeys();
    }

    protected abstract OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse);
}
