package com.musicpedia.musicpediaapi.domain.auth.dto;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    String getIdToken();
}
