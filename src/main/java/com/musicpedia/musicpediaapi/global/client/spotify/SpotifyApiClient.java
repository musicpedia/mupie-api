package com.musicpedia.musicpediaapi.global.client.spotify;

import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumTrack;
import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyAlbumWithTracks;
import com.musicpedia.musicpediaapi.domain.album.dto.SpotifyRequestTrack;
import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtist;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbum;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtist;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SpotifyApiClient {
    @Value("${oauth.spotify.url.api}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final HttpEntity<?> request;

    public SpotifyApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Accept-Language", "ko-KR");

        request = new HttpEntity<>(httpHeaders);
    }


    // 앨범 조회
    public SpotifyAlbumWithTracks requestAlbum(String accessToken, String albumId) {
        String url = apiUrl + "/v1/albums/" + albumId;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        SpotifyAlbumWithTracks spotifyAlbumWithTracks = restTemplate.exchange(url, HttpMethod.GET, request, SpotifyAlbumWithTracks.class).getBody();
        Objects.requireNonNull(spotifyAlbumWithTracks).setTrackList(getAllTracks(accessToken, albumId));
        return spotifyAlbumWithTracks;
    }

    // 앨범 조회 시, 앨범에 포함된 모든 곡들 불러오기
    private List<SpotifyAlbumTrack> getAllTracks(String accessToken, String albumId) {
        List<SpotifyAlbumTrack> allTracks = new ArrayList<>();
        String url = apiUrl + "/v1/albums/" + albumId + "/tracks";
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        int limit = 0;
        while (url != null && limit < 20) {
            // Spotify API 호출
            SpotifyRequestTrack response = restTemplate.exchange(url, HttpMethod.GET, request, SpotifyRequestTrack.class).getBody();

            // 응답에서 트랙 정보 추출
            List<SpotifyAlbumTrack> tracks = Objects.requireNonNull(response).getItems();

            // 전체 트랙 리스트에 추가
            allTracks.addAll(tracks);

            // 다음 페이지의 URL 가져오기 (pagination 처리)
            url = response.getNext();

            limit ++;
        }

        return allTracks;
    }


    // 아티스트 조회
    public SpotifyArtist requestArtist(String accessToken, String artistId) {
        String url = apiUrl + "/v1/artists/" + artistId;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifyArtist.class).getBody();
    }

    // 아티스트 앨범 조회
    public SpotifySearchAlbum requestArtistAlbums(
            String accessToken,
            String artistId,
            List<String> albumTypes,
            long offset,
            int limit
    ) {
        String url = apiUrl
                + "/v1/artists/"
                + artistId +
                "/albums" +
                "?include_groups=" + String.join(",", albumTypes) +
                "&limit=" + limit +
                "&offset=" + offset;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifySearchAlbum.class).getBody();
    }

    // 트랙 조회
    public SpotifyTrack requestTrack(String accessToken, String trackId) {
        String url = apiUrl + "/v1/tracks/" + trackId;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifyTrack.class).getBody();
    }

    // 검색
    public SpotifySearchAlbumTrackArtist search(
            String accessToken,
            String keyword,
            long offset,
            int limit,
            List<String> types
    ) {
        String url = apiUrl +
                "/v1/search?q=" + keyword +
                "&type=" + String.join(",", types) +
                "&offset=" + offset +
                "&limit=" + limit;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifySearchAlbumTrackArtist.class).getBody();
    }
}
