package com.musicpedia.musicpediaapi.domain.auth.client;

import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;

public interface OAuthClient {
    OIDCPublicKeysResponse getOIDCOpenKeys();
}
