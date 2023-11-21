package com.musicpedia.musicpediaapi.domain.artist.service;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {
    private static final List<String> ALBUM = List.of("album");
    private static final List<String> SINGLE = List.of("single");
    private static final List<String> COMPILATION = List.of("compilation");
    private static final List<String> APPEARS_ON = List.of("appears_on");

    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyTokenProvider spotifyTokenProvider;
    private final MemberRepository memberRepository;

    private String accessToken;

    public SpotifyArtist getArtistInfo(long memberId, String artistId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtist(accessToken, artistId);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtist(accessToken, artistId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    public SpotifySearchAlbum getArtistAlbums(long memberId, String artistId, long offset, int limit) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, ALBUM, offset, limit);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, ALBUM, offset, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 앨범 정보 조회 실패");
        }
    }

    public SpotifySearchAlbum getArtistSingles(long memberId, String artistId, long offset, int limit) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, SINGLE, offset, limit);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, SINGLE, offset, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 앨범 정보 조회 실패");
        }
    }

    public SpotifySearchAlbum getArtistCompilations(long memberId, String artistId, long offset, int limit) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, COMPILATION, offset, limit);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, COMPILATION, offset, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 앨범 정보 조회 실패");
        }
    }

    public SpotifySearchAlbum getArtistAppearsOn(long memberId, String artistId, long offset, int limit) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, APPEARS_ON, offset, limit);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtistAlbums(accessToken, artistId, APPEARS_ON, offset, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 앨범 정보 조회 실패");
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
}
