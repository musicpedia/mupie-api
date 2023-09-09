package com.musicpedia.musicpediaapi.domain.auth.dto;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
