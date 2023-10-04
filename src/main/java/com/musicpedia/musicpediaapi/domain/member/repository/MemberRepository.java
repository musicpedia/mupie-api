package com.musicpedia.musicpediaapi.domain.member.repository;

import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByOauthInfo(OAuthInfo oAuthInfo);
}
