package com.musicpedia.musicpediaapi.domain.auth.helper.apple;

import com.musicpedia.musicpediaapi.domain.auth.client.AppleOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppleOAuthHelper extends OAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;

    @Value("${oauth.apple.url.auth}")
    private String iss;

    @Value("${oauth.apple.client-ids}")
    private List<String> audiences;

    public AppleOAuthHelper(AppleOAuthClient appleOAuthClient, OAuthOIDCHelper oauthOIDCHelper) {
        super(appleOAuthClient);
        this.oauthOIDCHelper = oauthOIDCHelper;
    }

    @Override
    protected OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        return oauthOIDCHelper.getPayloadFromIdToken(token, iss, audiences, oidcPublicKeysResponse);
    }
}
