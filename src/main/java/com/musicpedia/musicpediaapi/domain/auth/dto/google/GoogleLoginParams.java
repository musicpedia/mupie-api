package com.musicpedia.musicpediaapi.domain.auth.dto.google;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GoogleLoginParams implements OAuthLoginParams {
    private String idToken;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getIdToken() {
        return this.idToken;
    }
}