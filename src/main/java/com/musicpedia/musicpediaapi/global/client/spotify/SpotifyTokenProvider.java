package com.musicpedia.musicpediaapi.global.client.spotify;

        import com.musicpedia.musicpediaapi.domain.artist.dto.SpotifyArtistInfo;
        import com.musicpedia.musicpediaapi.global.dto.spotify.SpotifyAccessToken;
        import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
        import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumTrackArtistInfo;
        import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchAlbumsInfo;
        import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchArtistsInfo;
        import com.musicpedia.musicpediaapi.domain.search.dto.SpotifySearchTracksInfo;
        import lombok.RequiredArgsConstructor;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.*;
        import org.springframework.stereotype.Component;
        import org.springframework.util.LinkedMultiValueMap;
        import org.springframework.util.MultiValueMap;
        import org.springframework.web.client.RestTemplate;

        import java.util.Objects;

@Component
public class SpotifyTokenProvider {
    private static final String GRANT_TYPE = "client_credentials";

    @Value("${oauth.spotify.url.auth}")
    private String authUrl;

    private final RestTemplate restTemplate;
    private final HttpEntity<?> request;

    public SpotifyTokenProvider(
            RestTemplate restTemplate,
            @Value("${oauth.spotify.client-id}") String clientId,
            @Value("${oauth.spotify.client-secret}") String clientSecret
    ) {
        this.restTemplate = restTemplate;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        request = new HttpEntity<>(body, httpHeaders);
    }

    public String requestAccessToken() {
        String url = authUrl + "/api/token";
        SpotifyAccessToken response = restTemplate.postForObject(url, request, SpotifyAccessToken.class);
        Objects.requireNonNull(response);
        return response.getAccessToken();
    }
}
