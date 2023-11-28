package com.musicpedia.musicpediaapi.domain.auth.service.apple;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.auth.helper.apple.AppleOAuthHelper;
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
public class AppleOAuthLoginService extends OAuthLoginService {
    private final MemberRepository memberRepository;
    public AppleOAuthLoginService(AppleOAuthHelper appleOAuthHelper, JwtUtil jwtUtil, MemberRepository memberRepository) {
        super(appleOAuthHelper, jwtUtil, OAuthProvider.APPLE);
        this.memberRepository = memberRepository;
    }

    @Override
    protected Member findOrCreateMember(OAuthInfo oAuthInfo, MemberDetail memberDetail) {
        return memberRepository.findByOauthInfo(oAuthInfo)
                .orElseGet(() -> createMember(oAuthInfo, memberDetail));
    }

    @Override
    protected Member createMember(OAuthInfo oAuthInfo, MemberDetail memberDetail) {
        Member member = Member.builder()
                .email(memberDetail.getEmail())
                .name("apple_"+ memberDetail.getEmail().substring(0, memberDetail.getEmail().indexOf('@')))
                .profileImage(memberDetail.getProfileImage())
                .oauthInfo(oAuthInfo)
                .build();

        return memberRepository.save(member);
    }
}