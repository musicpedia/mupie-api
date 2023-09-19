package com.musicpedia.musicpediaapi.domain.auth.dto.kakao;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
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
