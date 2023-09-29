package com.musicpedia.musicpediaapi.global.client.spotify;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtistInfo;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumsInfo;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtistsInfo;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTracksInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public ResponseEntity<SpotifyArtistInfo> requestArtist(String accessToken, String artistId) {
        String url = apiUrl + "/v1/artists/" + artistId;
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifyArtistInfo.class);
    }

    public ResponseEntity<SpotifySearchAlbumTrackArtistInfo> search(
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
        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifySearchAlbumTrackArtistInfo.class);
    }
}
