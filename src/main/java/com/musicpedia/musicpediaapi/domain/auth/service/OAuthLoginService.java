package com.musicpedia.musicpediaapi.domain.auth.service;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.auth.helper.OAuthHelper;
import com.musicpedia.musicpediaapi.domain.member.dto.response.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public abstract class OAuthLoginService {
    private final OAuthHelper oAuthHelper;
    private final JwtUtil jwtUtil;
    private final OAuthProvider oAuthProvider;

    private static final String GRANT_TYPE = "Bearer";

    public AuthTokens login(OAuthLoginParams oAuthLoginParams) {
        String idToken = oAuthLoginParams.getIdToken();
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(idToken);

        OAuthInfo oauthInfo = createOAuthInfo(oidcDecodePayload);

        MemberDetail memberDetail = createMemberDetail(oidcDecodePayload);

        Long memberId = findOrCreateMember(oauthInfo, memberDetail);

        return generateAuthTokens(memberId);
    }

    private OIDCDecodePayload getOIDCDecodePayload(String token) {
        return oAuthHelper.getOIDCDecodePayload(token);
    }

    private OAuthInfo createOAuthInfo(OIDCDecodePayload oidcDecodePayload) {
        return OAuthInfo.builder()
                .provider(oAuthProvider)
                .oid(oidcDecodePayload.getSub())
                .build();
    }

    private MemberDetail createMemberDetail(OIDCDecodePayload oidcDecodePayload) {
        return MemberDetail.builder()
                .email(oidcDecodePayload.getEmail())
                .profileImage(oidcDecodePayload.getPicture())
                .name(Optional.ofNullable(oidcDecodePayload.getName()).orElse(oidcDecodePayload.getNickname()))
                .build();
    }

    protected abstract Long findOrCreateMember(OAuthInfo oAuthInfo, MemberDetail memberDetail);

    protected abstract Long newMember(OAuthInfo oAuthInfo, MemberDetail memberDetail);

    private AuthTokens generateAuthTokens(Long memberId) {
        String accessToken = jwtUtil.generateAccessToken(memberId);
        String refreshToken = jwtUtil.generateRefreshToken(memberId);
        return AuthTokens.of(accessToken, refreshToken, GRANT_TYPE);
    }
}
