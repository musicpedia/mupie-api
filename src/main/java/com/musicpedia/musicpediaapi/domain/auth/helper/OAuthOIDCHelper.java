package com.musicpedia.musicpediaapi.domain.auth.helper;

import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeyDto;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import com.musicpedia.musicpediaapi.global.util.JwtOIDCProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthOIDCHelper {
    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromUnsignedIdToken(String token, String iss, List<String> audiences) {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, audiences);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token,
            String iss,
            List<String> audiences,
            OIDCPublicKeysResponse oidcPublicKeysResponse
    ) {
        String kid = getKidFromUnsignedIdToken(token, iss, audiences);
        OIDCPublicKeyDto oidcPublicKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();
        return jwtOIDCProvider.getOIDCTokenBody(
                token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }
}
