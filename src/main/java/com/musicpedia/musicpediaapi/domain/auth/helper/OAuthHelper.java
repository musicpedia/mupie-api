package com.musicpedia.musicpediaapi.domain.auth.helper;

import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;

public abstract class OAuthHelper {
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcOpenKeys = getOIDCOpenKeys();

        return getPayloadFromIdToken(token, oidcOpenKeys);
    }

    protected abstract OIDCPublicKeysResponse getOIDCOpenKeys();

    protected abstract OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse);
}
