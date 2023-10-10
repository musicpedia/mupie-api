package com.musicpedia.musicpediaapi.domain.auth.service.apple;

import com.musicpedia.musicpediaapi.domain.auth.dto.OAuthLoginParams;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.auth.helper.apple.AppleOAuthHelper;
import com.musicpedia.musicpediaapi.domain.member.dto.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.global.dto.AuthTokens;
import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppleOAuthLoginService {
    private final AppleOAuthHelper appleOAuthHelper;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private static final String GRANT_TYPE = "Bearer";

    public AuthTokens login(OAuthLoginParams oAuthLoginParams) {
        String idToken = oAuthLoginParams.getIdToken();
        OIDCDecodePayload oidcDecodePayload = appleOAuthHelper.getOIDCDecodePayload(idToken);

        OAuthInfo oauthInfo = OAuthInfo.builder()
                .provider(OAuthProvider.APPLE)
                .oid(oidcDecodePayload.getSub())
                .build();

        MemberDetail memberDetail = MemberDetail.builder()
                .email(oidcDecodePayload.getEmail())
                .profileImage(oidcDecodePayload.getPicture())
                .name(Optional.ofNullable(oidcDecodePayload.getName()).orElse(oidcDecodePayload.getNickname()))
                .build();

        Long memberId = findOrCreateMember(oauthInfo, memberDetail);

        return generateAuthTokens(memberId);
    }

    private Long findOrCreateMember(OAuthInfo oauthInfo, MemberDetail memberDetail) {
        return memberRepository.findByOauthInfo(oauthInfo)
                .map(Member::getId)
                .orElseGet(() -> newMember(oauthInfo, memberDetail));
    }

    private Long newMember(OAuthInfo oauthInfo, MemberDetail memberDetail) {
        Member member = Member.builder()
                .email(memberDetail.getEmail())
                .name("apple_"+ memberDetail.getEmail().substring(0, memberDetail.getEmail().indexOf('@')))
                .profileImage(memberDetail.getProfileImage())
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