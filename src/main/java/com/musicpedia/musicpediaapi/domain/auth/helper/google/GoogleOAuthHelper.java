package com.musicpedia.musicpediaapi.domain.auth.helper.google;

import com.musicpedia.musicpediaapi.domain.auth.client.GoogleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoogleOAuthHelper extends OAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;
    @Value("${oauth.google.url.auth}")
    private String iss;

    @Value("${oauth.google.client-ids}")
    private List<String> audiences;

    public GoogleOAuthHelper(GoogleOAuthClient googleOAuthClient, OAuthOIDCHelper oauthOIDCHelper) {
        super(googleOAuthClient);
        this.oauthOIDCHelper = oauthOIDCHelper;
    }

    @Override
    protected OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        return oauthOIDCHelper.getPayloadFromIdToken(token, iss, audiences, oidcPublicKeysResponse);
    }
}
