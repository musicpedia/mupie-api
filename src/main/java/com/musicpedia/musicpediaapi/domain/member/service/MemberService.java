package com.musicpedia.musicpediaapi.domain.member.service;

import com.musicpedia.musicpediaapi.domain.member.dto.MemberInfo;
import com.musicpedia.musicpediaapi.domain.member.exception.MemberNotFoundException;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfo getMemberInfo(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."))
                .toMemberInfo();
    }
}
