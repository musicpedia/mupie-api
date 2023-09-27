package com.musicpedia.musicpediaapi.domain.artist.service;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import com.musicpedia.musicpediaapi.domain.auth.client.SpotifyApiClient;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.exception.MemberNotFoundException;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {
    private final SpotifyApiClient spotifyApiClient;
    private final MemberRepository memberRepository;

    private String accessToken;

    public ResponseEntity<SpotifyArtistInfo> getArtistInfo(long memberId, String artistId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestArtistInfo(accessToken, artistId);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyApiClient.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestArtistInfo(accessToken, artistId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("아티스트 정보 조회 실패");
        }
    }

    private String findOrCreateAccessToken(Member member) {
        accessToken = member.getSpotifyAccessToken();

        if (accessToken == null) {
            accessToken = spotifyApiClient.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
        }

        return accessToken;
    }
}
