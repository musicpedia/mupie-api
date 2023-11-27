package com.musicpedia.musicpediaapi.member;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("[Repository] 아이디로 회원 조회 - 성공")
    public void Repo_아이디로_회원_조회_성공() {
        // given
        Member member = testMemberBuilder();
        Member savedMember = memberRepository.save(member);

        // when
        Long id = savedMember.getId();
        Member foundMember = memberRepository.findById(id)
                .orElse(Member.builder()
                        .name("찾을 수 없는 회원")
                        .build());

        // then
        assertThat(foundMember.getName()).isEqualTo("김뮤피");
    }

    @Test
    @DisplayName("[Repository] OAuth 정보로 회원 조회 - 성공")
    public void Repo_OAuth_정보로_회원_조회_성공() {
        // given
        Member member = testMemberBuilder();
        memberRepository.save(member);

        // when
        Member foundMember = memberRepository.findByOauthInfo(
                    OAuthInfo.builder()
                            .provider(OAuthProvider.GOOGLE)
                            .oid("googleoauthid")
                            .build()
                )
                .orElse(Member.builder()
                        .name("찾을 수 없는 회원")
                        .build());

        // then
        assertThat(foundMember.getName()).isEqualTo("김뮤피");
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("[Repository] 회원 탈퇴 테스트 - 성공")
    public void Repo_회원_탈퇴_테스트_성공() {
        // Given
        Member member = testMemberBuilder();
        Member savedMember = memberRepository.save(member);
        Long id = savedMember.getId();

        // When
        memberRepository.delete(savedMember);
        Optional<Member> foundMember = memberRepository.findById(id);

        // Then
        assertThat(foundMember)
                .isEqualTo(Optional.empty());
    }

    private static Member testMemberBuilder() {
        return Member.builder()
                .description("검정치마 좋아합니다.")
                .email("mupie@gmail.com")
                .name("김뮤피")
                .profileImage("this is profile image")
                .oauthInfo(
                        OAuthInfo.builder()
                                .provider(OAuthProvider.GOOGLE)
                                .oid("googleoauthid")
                                .build()
                )
                .build();
    }
}
