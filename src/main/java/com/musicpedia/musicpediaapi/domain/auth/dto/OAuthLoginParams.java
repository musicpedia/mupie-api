package com.musicpedia.musicpediaapi.domain.auth.dto;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
