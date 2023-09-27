package com.musicpedia.musicpediaapi.domain.search.service;

import com.musicpedia.musicpediaapi.domain.auth.client.SpotifyApiClient;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.exception.MemberNotFoundException;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtistInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final SpotifyApiClient spotifyApiClient;
    private final MemberRepository memberRepository;

    private String accessToken;

    public SpotifySearchAlbumTrackArtistInfo getAllSearchInfo(long memberId, String keyword, long offset, int limit) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.searchAlbumTrackArtist(accessToken, keyword, offset, limit).getBody();
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyApiClient.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.searchAlbumTrackArtist(accessToken, keyword, offset, limit).getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    private String findOrCreateAccessToken(Member member) {
        Optional<String> spotifyAccessToken = Optional.ofNullable(member.getSpotifyAccessToken());

        return spotifyAccessToken.orElseGet(() -> {
            accessToken = spotifyApiClient.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return accessToken;
        });
    }
}
