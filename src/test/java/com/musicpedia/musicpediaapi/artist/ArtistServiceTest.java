package com.musicpedia.musicpediaapi.artist;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.artist.service.ArtistService;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SpotifyTokenProvider spotifyTokenProvider;

    @Mock
    private SpotifyApiClient spotifyApiClient;

    @InjectMocks
    private ArtistService artistService;

    @Test
    @DisplayName("[Service] 아티스트 아이디로 아티스트 정보 조회 - 성공")
    public void 아티스트_아이디로_아티스트_정보_조회_성공() {
        // given
        Member member = testUserBuilder(); // Member 객체를 초기화하고 필요한 값 설정
        String artistId = "0TnOYISbd1XYRBk9myaseg";
        SpotifyArtist spotifyArtist = new SpotifyArtist();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(spotifyTokenProvider.requestAccessToken()).thenReturn("accessToken");
        when(spotifyApiClient.requestArtist(anyString(), anyString())).thenReturn(spotifyArtist);

        // when
        SpotifyArtist result = artistService.getArtistInfo(1L, artistId);

        // then
        assertNotNull(result);
    }

    private Member testUserBuilder() {
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