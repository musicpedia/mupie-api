package com.musicpedia.musicpediaapi.global.helper;

import com.musicpedia.musicpediaapi.global.dto.OIDCDecodePayload;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeyDto;
import com.musicpedia.musicpediaapi.global.dto.OIDCPublicKeysResponse;
import com.musicpedia.musicpediaapi.global.util.JwtOIDCProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthOIDCHelper {
    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token,
            String iss,
            String aud,
            OIDCPublicKeysResponse oidcPublicKeysResponse
    ) {
        String kid = getKidFromUnsignedIdToken(token, iss, aud);
        OIDCPublicKeyDto oidcPublicKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();
        return jwtOIDCProvider.getOIDCTokenBody(
                token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }
}
