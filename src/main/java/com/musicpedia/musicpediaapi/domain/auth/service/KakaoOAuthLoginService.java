package com.musicpedia.musicpediaapi.domain.auth.service;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.member.dto.MemberInfo;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.helper.KakaoOAuthHelper;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOAuthLoginService {
    private final KakaoOAuthHelper kakaoOAuthHelper;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private static final String GRANT_TYPE = "Bearer";

    public AuthTokens login(OAuthLoginParams oAuthLoginParams) {
        String idToken = oAuthLoginParams.getIdToken();
        OIDCDecodePayload oidcDecodePayload = kakaoOAuthHelper.getOIDCDecodePayload(idToken);

        OAuthInfo oauthInfo = OAuthInfo.builder()
                .provider(OAuthProvider.KAKAO)
                .oid(oidcDecodePayload.getSub())
                .build();

        MemberInfo memberInfo = MemberInfo.builder()
                .email(oidcDecodePayload.getEmail())
                .profile_image(oidcDecodePayload.getPicture())
                .nickname(oidcDecodePayload.getNickname())
                .build();

        Long memberId = findOrCreateMember(oauthInfo, memberInfo);

        return generateAuthTokens(memberId);
    }

    private Long findOrCreateMember(OAuthInfo oauthInfo, MemberInfo memberInfo) {
        return memberRepository.findByOauthInfo(oauthInfo)
                .map(Member::getId)
                .orElseGet(() -> newMember(oauthInfo, memberInfo));
    }

    private Long newMember(OAuthInfo oauthInfo, MemberInfo memberInfo) {
        Member member = Member.builder()
                .email(memberInfo.getEmail())
                .nickname(memberInfo.getNickname())
                .profileImage(memberInfo.getProfile_image())
                .oauthInfo(oauthInfo)
                .build();

        return memberRepository.save(member).getId();
    }

    private AuthTokens generateAuthTokens(Long memberId) {
        String accessToken = jwtUtil.generateAccessToken(memberId);
        String refreshToken = jwtUtil.generateRefreshToken(memberId);
        return AuthTokens.of(accessToken, refreshToken, GRANT_TYPE);
    }
}