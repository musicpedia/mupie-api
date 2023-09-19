package com.musicpedia.musicpediaapi.domain.member.entity;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthInfo {
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    private String oid;

    @Builder
    public OAuthInfo(OAuthProvider provider, String oid) {
        this.provider = provider;
        this.oid = oid;
    }
}