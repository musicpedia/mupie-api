package com.musicpedia.musicpediaapi.like.artist;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.artist.liked_artist.entity.LikedArtist;
import com.musicpedia.musicpediaapi.domain.artist.liked_artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
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
        LikedArtist likedArtist = testLikedArtistBuilder();

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

        LikedArtist likedArtist = testLikedArtistBuilder();

        likedArtistRepository.save(likedArtist);

        // when
        LikedArtist foundArtist = likedArtistRepository.findBySpotifyIdAndMember(artistId, member)
                .orElseThrow(() -> new NoResultException("해당하는 아티스트를 조회할 수 없습니다."));

        // then
        Assertions.assertThat(foundArtist.getName()).isEqualTo("뉴진스");
    }

    @Test
    @DisplayName("[Repository] 좋아하는 아티스트 수 조회 - 성공")
    public void Repo_좋아하는_아티스트_수_조회_성공() {
        // given
        LikedArtist likedArtist1 = testLikedArtistBuilder();

        LikedArtist likedArtist2 = LikedArtist.builder()
                .name("Ed Sheeran")
                .spotifyId("0FxQZABdc1AWRAQ9aosneq")
                .thumbnail("Ed Sheeran thumbnail")
                .member(member)
                .build();

        likedArtistRepository.save(likedArtist1);
        likedArtistRepository.save(likedArtist2);

        // when
        long likedArtistCnt = likedArtistRepository.countAllByMember(member);

        // then
        Assertions.assertThat(likedArtistCnt).isEqualTo(2);
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

    private LikedArtist testLikedArtistBuilder() {
        return LikedArtist.builder()
                .name("뉴진스")
                .spotifyId("0TnOYISbd1XYRBk9myaseg")
                .thumbnail("new jeans thumbnail")
                .member(member)
                .build();
    }
}
