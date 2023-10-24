package com.musicpedia.musicpediaapi.like.artist;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.like.artist.entity.LikedArtist;
import com.musicpedia.musicpediaapi.domain.like.artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import jakarta.persistence.NoResultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikedArtistRepositoryTest {
    @Autowired
    private LikedArtistRepository likedArtistRepository;

    private Member member;

    @BeforeEach
    public void 회원_초기화() throws NoSuchFieldException, IllegalAccessException {
        Member savedMember = testMemberBuilder();
        Class<?> memberClass = Member.class;
        Field idField = memberClass.getDeclaredField("id");
        idField.setAccessible(true); // private 필드에 접근 가능하게 설정
        idField.set(savedMember, 1L); // ID 값을 Reflection을 사용하여 설정

        member = savedMember;
    }

    @Test
    @DisplayName("[Repository] 좋아하는 아티스트 저장 - 성공")
    public void Repo_좋아하는_아티스트_저장_성공() {
        // given
        LikedArtist likedArtist = LikedArtist.builder()
                .name("뉴진스")
                .spotifyId("0TnOYISbd1XYRBk9myaseg")
                .thumbnail("new jeans thumbnail")
                .member(member)
                .build();

        LikedArtist savedArtist = likedArtistRepository.save(likedArtist);

        // when
        Long id = savedArtist.getId();
        LikedArtist foundArtist = likedArtistRepository.findById(id)
                .orElseThrow(() -> new NoResultException("해당하는 아티스트를 조회할 수 없습니다."));

        // then
        Assertions.assertThat(foundArtist.getName()).isEqualTo("뉴진스");
    }

    @Test
    @DisplayName("[Repository] 좋아하는 아티스트 단일 조회 - 성공")
    public void Repo_좋아하는_아티스트_단일_조회_성공() {
        // given
        String artistId = "0TnOYISbd1XYRBk9myaseg";

        LikedArtist likedArtist = LikedArtist.builder()
                .name("뉴진스")
                .spotifyId(artistId)
                .thumbnail("new jeans thumbnail")
                .member(member)
                .build();

        likedArtistRepository.save(likedArtist);

        // when
        LikedArtist foundArtist = likedArtistRepository.findBySpotifyIdAndMember(artistId, member)
                .orElseThrow(() -> new NoResultException("해당하는 아티스트를 조회할 수 없습니다."));

        // then
        Assertions.assertThat(foundArtist.getName()).isEqualTo("뉴진스");
    }

    private Member testMemberBuilder() {
        return Member.builder()
                .description("검정치마 좋아합니다.")
                .email("mupie@gmail.com")
                .name("김뮤피")
                .profileImage("this is profile image")
                .oauthInfo(
                        OAuthInfo.builder()
                                .provider(OAuthProvider.GOOGLE)
                                .oid("oauth id")
                                .build()
                )
                .build();
    }
}