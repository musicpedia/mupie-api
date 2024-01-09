package com.musicpedia.musicpediaapi.rating;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository.ReplyCommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingUpdateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ReplyCommentRepository replyCommentRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    @DisplayName("[Service] 평가 저장 - 성공")
    public void 평가_저장_성공() {
        // given
        Member member = testMemberBuilder(); // Member 객체를 초기화하고 필요한 값 설정

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.empty());
        given(ratingRepository.save(any()))
                .willReturn(testAlbumRatingBuilder());

        // when
        RatingDetail ratingDetail = ratingService.saveRating(1L, testCreateRequest());

        // then
        assertThat(ratingDetail.getName()).isEqualTo("Austin");
    }

    @Test
    @DisplayName("[Service] 평가가 있는 점수(별점, 평균) 조회 - 성공")
    public void 평가가_있는_경우_조회_성공() {
        // given
        Member member = testMemberBuilder();
        Rating rating = testAlbumRatingBuilder();
        double averageScore = 3.2;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.of(rating));
        given(ratingRepository.calculateAverageScoreBySpotifyId(anyString()))
                .willReturn(averageScore);

        // when
        Score score = ratingService.getScore(1L, "1tfAfSTJHXtmgkzDwBasOp");
        String ratingScore = score.getRatingScore();
        String averageRatingScore = score.getAverageScore();

        // then
        assertThat(ratingScore).isEqualTo("4.5");
        assertThat(averageRatingScore).isEqualTo("3.2");
    }

    @Test
    @DisplayName("[Service] 평가가 없는 조회 - 성공")
    public void 평가가_없는_경우_조회_성공() {
        // given
        Member member = testMemberBuilder();
        double averageScore = 3.2;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.empty());
        given(ratingRepository.calculateAverageScoreBySpotifyId(anyString()))
                .willReturn(averageScore);

        // when
        Score score = ratingService.getScore(1L, "1tfAfSTJHXtmgkzDwBasOp");
        String ratingScore = score.getRatingScore();
        String averageRatingScore = score.getAverageScore();

        // then
        assertThat(ratingScore).isEqualTo("0");
        assertThat(averageRatingScore).isEqualTo("3.2");
    }

    @Test
    @DisplayName("[Service] 평균_점수가_없는_조회 - 성공")
    public void 평균_점수가_없는_조회_성공() {
        // given
        Member member = testMemberBuilder();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.empty());
        given(ratingRepository.calculateAverageScoreBySpotifyId(anyString()))
                .willReturn(null);

        // when
        Score score = ratingService.getScore(1L, "1tfAfSTJHXtmgkzDwBasOp");
        String ratingScore = score.getRatingScore();
        String averageRatingScore = score.getAverageScore();

        // then
        assertThat(ratingScore).isEqualTo("0");
        assertThat(averageRatingScore).isEqualTo("0");
    }

    @Test
    @DisplayName("[Service] 평가 수정 - 성공")
    public void 평가_수정_성공() {
        // given
        Member member = testMemberBuilder();
        Rating rating = testAlbumRatingBuilder();
        RatingUpdateRequest request = testUpdateRequest();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.of(rating));
        given(commentRepository.findAllBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Collections.emptyList());
        given(replyCommentRepository.findAllBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Collections.emptyList());

        // when
        ratingService.updateRating(1L, request);

        // then
        assertThat(rating.getScore()).isEqualTo(request.getScore());
    }

    @Test
    @DisplayName("[Service] 평가 삭제 - 성공")
    public void 평가_삭제_성공() {
        // given
        Member member = testMemberBuilder();
        Rating rating = testAlbumRatingBuilder();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(ratingRepository.findBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Optional.of(rating));
        doNothing().when(ratingRepository).delete(rating);
        given(commentRepository.findAllBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Collections.emptyList());
        given(replyCommentRepository.findAllBySpotifyIdAndMember(anyString(), any()))
                .willReturn(Collections.emptyList());

        String spotifyId = rating.getSpotifyId();

        // when
        ratingService.deleteRating(1L, spotifyId);

        // then
        verify(ratingRepository, times(1)).delete(rating);
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
                .build();
    }

    private RatingCreateRequest testCreateRequest() {
        RatingCreateRequest ratingCreateRequest = new RatingCreateRequest();
        ratingCreateRequest.setSpotifyId("1tfAfSTJHXtmgkzDwBasOp");
        ratingCreateRequest.setScore("4.5");
        ratingCreateRequest.setName("Austin");
        ratingCreateRequest.setThumbnail("https://i.scdn.co/image/ab67616d0000b2735f4acf9723395f91ce0a9b51");
        ratingCreateRequest.setReleaseDate("2023-10-06");
        return ratingCreateRequest;
    }

    private RatingUpdateRequest testUpdateRequest() {
        RatingUpdateRequest ratingUpdateRequest = new RatingUpdateRequest();
        ratingUpdateRequest.setSpotifyId("1tfAfSTJHXtmgkzDwBasOp");
        ratingUpdateRequest.setScore("2.5");
        return ratingUpdateRequest;
    }
}
