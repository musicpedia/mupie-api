package com.musicpedia.musicpediaapi.domain.auth.helper.apple;

import com.musicpedia.musicpediaapi.domain.auth.client.AppleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleOAuthHelper extends OAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;

    @Value("${oauth.apple.url.auth}")
    private String iss;

    @Value("${oauth.apple.client-id}")
    private String aud;

    public AppleOAuthHelper(AppleOAuthClient appleOAuthClient, OAuthOIDCHelper oauthOIDCHelper) {
        super(appleOAuthClient);
        this.oauthOIDCHelper = oauthOIDCHelper;
    }

    @Override
    protected OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        return oauthOIDCHelper.getPayloadFromIdToken(token, iss, aud, oidcPublicKeysResponse);
    }
}
