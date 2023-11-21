package com.musicpedia.musicpediaapi.domain.search.service;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTrack;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    private static final List<String> ALL_TYPES = List.of("album", "artist", "track");
    private static final List<String> ALBUM = List.of("album");
    private static final List<String> ARTIST = List.of("artist");
    private static final List<String> TRACK = List.of("track");

    public SpotifySearchAlbumTrackArtist getAllSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.search(accessToken, keyword, offset, limit, ALL_TYPES);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.search(accessToken, keyword, offset, limit, ALL_TYPES);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchAlbum getAlbumSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, ALBUM)).getAlbums();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, ALBUM)).getAlbums();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchArtist getArtistSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, ARTIST)).getArtists();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, ARTIST)).getArtists();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchTrack getTrackSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = validateByMemberId(memberId);
        accessToken = findOrCreateAccessToken(member);

        try {
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, TRACK)).getTracks();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return Objects.requireNonNull(spotifyApiClient.search(accessToken, keyword, offset, limit, TRACK)).getTracks();
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
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
    }
}
