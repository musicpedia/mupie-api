package com.musicpedia.musicpediaapi.rating;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingRepositoryTest {
    @Autowired
    private RatingRepository ratingRepository;

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
    @DisplayName("[Repository] 평가 단일 조회 - 성공")
    public void Repo_평가_단일_조회_성공() {
        // given
        Rating rating = testAlbumRatingBuilder();

        ratingRepository.save(rating);

        // when
        Rating foundRating = ratingRepository.findBySpotifyIdAndMember(rating.getSpotifyId(), member)
                .orElseThrow(() -> new NoResultException("해당하는 평가 항목을 조회할 수 없습니다."));

        // then
        assertThat(foundRating.getName()).isEqualTo("Austin");
    }

    @Test
    @DisplayName("[Repository] 항목에 따른 평가 수 조회 - 성공")
    public void Repo_항목에_따른_평가_수_조회_성공() {
        // given
        Rating albumRating = testAlbumRatingBuilder();
        Rating trackRating = testTrackRatingBuilder();

        ratingRepository.save(albumRating);
        ratingRepository.save(trackRating);

        // when
        long albumRatingCnt = ratingRepository.countAllByMemberAndType(member, Type.ALBUM);
        long trackRatingCnt = ratingRepository.countAllByMemberAndType(member, Type.TRACK);

        // then
        Assertions.assertThat(albumRatingCnt).isEqualTo(1);
        Assertions.assertThat(trackRatingCnt).isEqualTo(1);
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

    private Rating testAlbumRatingBuilder() {
        return Rating.builder()
                .type(Type.ALBUM)
                .score("4.5")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .name("Austin")
                .thumbnail("https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
                .releaseDate("2023-10-06")
                .member(member)
                .build();
    }

    private Rating testTrackRatingBuilder() {
        return Rating.builder()
                .type(Type.TRACK)
                .score("4.0")
                .spotifyId("11dFghVXANMlKmJXsNCbNl")
                .name("sunflower")
                .thumbnail("https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51")
                .releaseDate("2023-10-06")
                .member(member)
                .build();
    }
}
