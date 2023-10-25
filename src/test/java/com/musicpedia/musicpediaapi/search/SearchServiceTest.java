package com.musicpedia.musicpediaapi.search;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTrack;
import com.musicpedia.musicpediaapi.domain.search.service.SearchService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SpotifyTokenProvider spotifyTokenProvider;

    @Mock
    private SpotifyApiClient spotifyApiClient;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("[Service] 키워드로 앨범 정보 검색 - 성공")
    public void 키워드로_앨범_정보_검색_성공() {
        // given
        Member member = this.testMemberBuilder();
        SpotifySearchAlbumTrackArtist searchAlbumResult = testSearchAlbumResult();
        String keyword = "Austin";
        long offset = 0;
        int limit = 20;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(spotifyTokenProvider.requestAccessToken())
                .willReturn("accessToken");
        given(spotifyApiClient.search(anyString(), anyString(), anyLong(), anyInt(), anyList()))
                .willReturn(searchAlbumResult);

        // when
        SpotifySearchAlbum result = searchService.getAlbumSearchInfo(1L, keyword, offset, limit);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("[Service] 키워드로 아티스트 정보 검색 - 성공")
    public void 키워드로_아티스트_정보_검색_성공() {
        // given
        Member member = this.testMemberBuilder();
        SpotifySearchAlbumTrackArtist searchArtistResult = testSearchArtistResult();
        String keyword = "뉴진스";
        long offset = 0;
        int limit = 20;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(spotifyTokenProvider.requestAccessToken())
                .willReturn("accessToken");
        given(spotifyApiClient.search(anyString(), anyString(), anyLong(), anyInt(), anyList()))
                .willReturn(searchArtistResult);

        // when
        SpotifySearchArtist result = searchService.getArtistSearchInfo(1L, keyword, offset, limit);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("[Service] 키워드로 트랙 정보 검색 - 성공")
    public void 키워드로_트랙_정보_검색_성공() {
        // given
        Member member = this.testMemberBuilder();
        SpotifySearchAlbumTrackArtist testSearchTrackResult = testSearchTrackResult();
        String keyword = "뉴진스";
        long offset = 0;
        int limit = 20;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(spotifyTokenProvider.requestAccessToken())
                .willReturn("accessToken");
        given(spotifyApiClient.search(anyString(), anyString(), anyLong(), anyInt(), anyList()))
                .willReturn(testSearchTrackResult);

        // when
        SpotifySearchTrack result = searchService.getTrackSearchInfo(1L, keyword, offset, limit);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("[Service] 키워드로 전체 검색 - 성공")
    public void 키워드로_전체_검색_성공() {
        // given
        Member member = this.testMemberBuilder();
        SpotifySearchAlbumTrackArtist searchResult = testSearchResult();
        String keyword = "뉴진스";
        long offset = 0;
        int limit = 20;

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(spotifyTokenProvider.requestAccessToken())
                .willReturn("accessToken");
        given(spotifyApiClient.search(anyString(), anyString(), anyLong(), anyInt(), anyList()))
                .willReturn(searchResult);

        // when
        SpotifySearchArtist result = searchService.getArtistSearchInfo(1L, keyword, offset, limit);

        // then
        assertNotNull(result);
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

    private SpotifySearchAlbumTrackArtist testSearchResult() {
        SpotifySearchAlbumTrackArtist spotifySearchAlbumTrackArtist = new SpotifySearchAlbumTrackArtist();
        spotifySearchAlbumTrackArtist.setAlbums(new SpotifySearchAlbum());
        spotifySearchAlbumTrackArtist.setArtists(new SpotifySearchArtist());
        spotifySearchAlbumTrackArtist.setTracks(new SpotifySearchTrack());
        return spotifySearchAlbumTrackArtist;
    }

    private SpotifySearchAlbumTrackArtist testSearchAlbumResult() {
        SpotifySearchAlbumTrackArtist spotifySearchAlbumTrackArtist = new SpotifySearchAlbumTrackArtist();
        spotifySearchAlbumTrackArtist.setAlbums(new SpotifySearchAlbum());
        return spotifySearchAlbumTrackArtist;
    }

    private SpotifySearchAlbumTrackArtist testSearchArtistResult() {
        SpotifySearchAlbumTrackArtist spotifySearchAlbumTrackArtist = new SpotifySearchAlbumTrackArtist();
        spotifySearchAlbumTrackArtist.setArtists(new SpotifySearchArtist());
        return spotifySearchAlbumTrackArtist;
    }

    private SpotifySearchAlbumTrackArtist testSearchTrackResult() {
        SpotifySearchAlbumTrackArtist spotifySearchAlbumTrackArtist = new SpotifySearchAlbumTrackArtist();
        spotifySearchAlbumTrackArtist.setTracks(new SpotifySearchTrack());
        return spotifySearchAlbumTrackArtist;
    }
}
