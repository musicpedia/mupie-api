package com.musicpedia.musicpediaapi.domain.auth.client;

import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
import com.musicpedia.musicpediaapi.domain.auth.dto.spotify.SpotifyAccessToken;
import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtistInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SpotifyApiClient {

    private static final String GRANT_TYPE = "client_credentials";

    @Value("${oauth.spotify.client-id}")
    private String clientId;

    @Value("${oauth.spotify.client-secret}")
    private String clientSecret;

    @Value("${oauth.spotify.url.auth}")
    private String authUrl;

    @Value("${oauth.spotify.url.api}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public OAuthProvider oAuthProvider() {
        return OAuthProvider.SPOTIFY;
    }

    public String requestAccessToken() {
        String url = authUrl + "/api/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        SpotifyAccessToken response = restTemplate.postForObject(url, request, SpotifyAccessToken.class);

        Objects.requireNonNull(response);
        return response.getAccessToken();
    }

    public ResponseEntity<SpotifyArtistInfo> requestArtistInfo(String accessToken, String artistId) {
        String url = apiUrl + "/v1/artists/";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        httpHeaders.set("Accept-Language", "ko-KR");

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url+artistId, HttpMethod.GET, request, SpotifyArtistInfo.class);
    }

    public ResponseEntity<SpotifySearchAlbumTrackArtistInfo> searchAlbumTrackArtist(String accessToken, String keyword, long offset, int limit) {
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("/v1/search?q=").append(keyword);
        queryStringBuilder.append("&type=track,album,artist");
        queryStringBuilder.append("&offset=").append(offset);
        queryStringBuilder.append("&limit=").append(limit);

        String url = apiUrl + queryStringBuilder;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        httpHeaders.set("Accept-Language", "ko-KR");

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, request, SpotifySearchAlbumTrackArtistInfo.class);
    }
}
