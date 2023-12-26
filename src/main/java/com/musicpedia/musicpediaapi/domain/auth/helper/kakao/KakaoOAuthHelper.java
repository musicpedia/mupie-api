package com.musicpedia.musicpediaapi.domain.auth.helper.kakao;

import com.musicpedia.musicpediaapi.domain.auth.client.KakaoOAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.client.OAuthClient;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthOIDCHelper;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KakaoOAuthHelper extends OAuthHelper {
    private final OAuthOIDCHelper oauthOIDCHelper;

    @Value("${oauth.kakao.url.auth}")
    private String iss;

    @Value("${oauth.kakao.client-ids}")
    private List<String> audiences;

    public KakaoOAuthHelper(KakaoOAuthClient kakaoOAuthClient, OAuthOIDCHelper oauthOIDCHelper) {
        super(kakaoOAuthClient);
        this.oauthOIDCHelper = oauthOIDCHelper;
    }

    @Override
    protected OIDCDecodePayload getPayloadFromIdToken(String token, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        return oauthOIDCHelper.getPayloadFromIdToken(token, iss, audiences, oidcPublicKeysResponse);
    }
}
