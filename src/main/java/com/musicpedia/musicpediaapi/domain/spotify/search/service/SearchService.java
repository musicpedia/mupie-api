package com.musicpedia.musicpediaapi.domain.spotify.search.service;

import com.musicpedia.musicpediaapi.domain.spotify.search.dto.SpotifySearchAlbumsInfo;
import com.musicpedia.musicpediaapi.domain.spotify.search.dto.SpotifySearchArtistsInfo;
import com.musicpedia.musicpediaapi.domain.spotify.search.dto.SpotifySearchTracksInfo;
import com.musicpedia.musicpediaapi.domain.spotify.search.dto.SpotifySearchAlbumTrackArtistInfo;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.exception.MemberNotFoundException;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyTokenProvider spotifyTokenProvider;
    private final MemberRepository memberRepository;

    private String accessToken;

    private final List<String> allTypes = new ArrayList<>(List.of("album", "artist", "track"));
    private final List<String> albumType = new ArrayList<>(List.of("album"));
    private final List<String> artistType = new ArrayList<>(List.of("artist"));
    private final List<String> trackType = new ArrayList<>(List.of("track"));

    public SpotifySearchAlbumTrackArtistInfo getAllSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.search(accessToken, keyword, offset, limit, allTypes).getBody();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.search(accessToken, keyword, offset, limit, allTypes).getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchAlbumsInfo getAlbumSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, albumType).getBody()).getAlbums();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, albumType).getBody()).getAlbums();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchArtistsInfo getArtistSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, artistType).getBody()).getArtists();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, artistType).getBody()).getArtists();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchTracksInfo getTrackSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, trackType).getBody()).getTracks();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, trackType).getBody()).getTracks();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    private String findOrCreateAccessToken(Member member) {
        Optional<String> spotifyAccessToken = Optional.ofNullable(member.getSpotifyAccessToken());

        return spotifyAccessToken.orElseGet(() -> {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return accessToken;
        });
    }

    private Member validateByMemberId(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."));
    }
}
