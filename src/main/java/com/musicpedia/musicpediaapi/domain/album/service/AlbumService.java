package com.musicpedia.musicpediaapi.domain.album.service;

import com.musicpedia.musicpediaapi.domain.album.dto.AlbumTrack;
import com.musicpedia.musicpediaapi.domain.album.dto.AlbumWithTracks;
import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumTrack;
import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumWithTracks;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyApiClient;
import com.musicpedia.musicpediaapi.global.client.spotify.SpotifyTokenProvider;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {
    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyTokenProvider spotifyTokenProvider;
    private final MemberRepository memberRepository;

    private final RatingService ratingService;

    private String accessToken;

    public AlbumWithTracks getAlbum(long memberId, String albumId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        accessToken = findOrCreateAccessToken(member);

        try {
            SpotifyAlbumWithTracks spotifyAlbumWithTracks = spotifyApiClient.requestAlbum(accessToken, albumId);
            return toAlbumWithTracks(spotifyAlbumWithTracks, memberId);
        } catch (HttpClientErrorException.Unauthorized e) {
            accessToken = spotifyTokenProvider.requestAccessToken();
            member.refreshAccessToken(accessToken);
            memberRepository.save(member);

            SpotifyAlbumWithTracks spotifyAlbumWithTracks = spotifyApiClient.requestAlbum(accessToken, albumId);
            return toAlbumWithTracks(spotifyAlbumWithTracks, memberId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("앨범 정보 조회 실패");
        }
    }

    private AlbumWithTracks toAlbumWithTracks(SpotifyAlbumWithTracks spotifyAlbumWithTracks, long memberId) {
        List<SpotifyAlbumTrack> spotifyAlbumTracks = spotifyAlbumWithTracks.getTrackList();

        List<String> spotifyAlbumTrackIds = getAlbumTrackIds(spotifyAlbumTracks);
        Map<String, Score> albumTrackScores = getAlbumTrackScores(memberId, spotifyAlbumTrackIds);
        List<AlbumTrack> albumTracks = getAlbumTracks(spotifyAlbumTracks, albumTrackScores);

        return spotifyAlbumWithTracks.toAlbumWithTracks(albumTracks);
    }

    private List<String> getAlbumTrackIds(List<SpotifyAlbumTrack> tracks) {
        return tracks
                .stream()
                .map(SpotifyAlbumTrack::getId)
                .toList();
    }

    private Map<String, Score> getAlbumTrackScores(long memberId, List<String> spotifyIds) {
        return ratingService.getScores(memberId, spotifyIds);
    }

    private List<AlbumTrack> getAlbumTracks(List<SpotifyAlbumTrack> tracks, Map<String, Score> scores) {
        List<AlbumTrack> albumTracks = new ArrayList<>();
        tracks.forEach(track -> albumTracks.add(getAlbumTrack(track, scores.get(track.getId()))));

        return albumTracks;
    }

    private AlbumTrack getAlbumTrack(SpotifyAlbumTrack track, Score score) {
        return AlbumTrack.builder()
                .albumTrack(track)
                .score(score)
                .build();
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
