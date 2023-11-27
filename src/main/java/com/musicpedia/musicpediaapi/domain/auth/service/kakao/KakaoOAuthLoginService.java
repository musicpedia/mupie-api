package com.musicpedia.musicpediaapi.domain.auth.service.kakao;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.auth.helper.kakao.KakaoOAuthHelper;
import com.musicpedia.musicpediaapi.domain.auth.service.OAuthLoginService;
import com.musicpedia.musicpediaapi.domain.member.dto.response.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KakaoOAuthLoginService extends OAuthLoginService {
    private final MemberRepository memberRepository;
    public KakaoOAuthLoginService(KakaoOAuthHelper kakaoOAuthHelper, JwtUtil jwtUtil, MemberRepository memberRepository) {
        super(kakaoOAuthHelper, jwtUtil, OAuthProvider.KAKAO);
        this.memberRepository = memberRepository;
    }

    @Override
    protected Long findOrCreateMember(OAuthInfo oAuthInfo, MemberDetail memberDetail) {
        return memberRepository.findByOauthInfo(oAuthInfo)
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfo, memberDetail));
    }

    @Override
    protected Long newMember(OAuthInfo oAuthInfo, MemberDetail memberDetail) {
        Member member = Member.builder()
                .email(memberDetail.getEmail())
                .name(memberDetail.getName())
                .profileImage(memberDetail.getProfileImage())
                .oauthInfo(oAuthInfo)
                .build();

        return memberRepository.save(member).getId();
    }
}