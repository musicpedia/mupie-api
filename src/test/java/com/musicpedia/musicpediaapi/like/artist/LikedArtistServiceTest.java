package com.musicpedia.musicpediaapi.like.artist;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistDetail;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.request.LikedArtistCreateRequest;
import com.musicpedia.musicpediaapi.domain.like.artist.entity.LikedArtist;
import com.musicpedia.musicpediaapi.domain.like.artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.like.artist.service.LikedArtistService;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikedArtistServiceTest {
    @Mock
    private LikedArtistRepository likedArtistRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private LikedArtistService likedArtistService;

    @Test
    @DisplayName("[Service] 좋아하는 아티스트 저장 - 성공")
    public void 좋아하는_아티스트_저장_성공() {
        // given
        Member member = testMemberBuilder(); // Member 객체를 초기화하고 필요한 값 설정
        SpotifyArtist spotifyArtist = new SpotifyArtist();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(likedArtistRepository.findBySpotifyIdAndMember(anyString(), any()))
                .thenReturn(Optional.of(testLikedArtistBuilder(member)));

        // when
        LikedArtistDetail likedArtistDetail = likedArtistService.likeArtist(1, testCreateRequest());

        // then
        assertThat(likedArtistDetail.getName()).isEqualTo("뉴진스");
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

    private LikedArtist testLikedArtistBuilder(Member member) {
        return LikedArtist.builder()
                .name("뉴진스")
                .spotifyId("0TnOYISbd1XYRBk9myaseg")
                .thumbnail("new jeans thumbnail")
                .member(member)
                .build();
    }

    private LikedArtistCreateRequest testCreateRequest() {
        LikedArtistCreateRequest likedArtistCreateRequest = new LikedArtistCreateRequest();
        likedArtistCreateRequest.setSpotifyId("0TnOYISbd1XYRBk9myaseg");
        likedArtistCreateRequest.setName("뉴진스");
        likedArtistCreateRequest.setThumbnail("new jeans thumbnail");
        return likedArtistCreateRequest;
    }
}
