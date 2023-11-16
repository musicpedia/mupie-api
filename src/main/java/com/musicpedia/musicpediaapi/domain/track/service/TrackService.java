package com.musicpedia.musicpediaapi.domain.track.service;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyTokenProvider spotifyTokenProvider;
    private final MemberRepository memberRepository;

    private String accessToken;

    @Cacheable(cacheManager = "weeklyRecommendationCacheManager", value = "weeklyRecommendation", key = "#trackId", condition = "@weeklyRecommendationService.getWeeklyRecommendationTrackIds().contains(#trackId)")
    public SpotifyTrack getTrack(long memberId, String trackId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            return spotifyApiClient.requestTrack(accessToken, trackId);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);
            return spotifyApiClient.requestTrack(accessToken, trackId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("트랙 정보 조회 실패");
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
