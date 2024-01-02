package com.musicpedia.musicpediaapi.interceptor;

import com.musicpedia.musicpediaapi.global.exception.TokenNotFoundException;
import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    private final long guestId;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AccessDeniedException {
        // Preflight인 경우 허용
        if(CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            throw new TokenNotFoundException("필수 헤더값이 없습니다.");
        }
        String token = jwtUtil.resolveToken(bearerToken);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        guestRequestCheck(request, memberId);

        // 추출한 멤버 ID를 요청 속성(attribute)에 저장하여 컨트롤러에서 사용할 수 있도록 함
        request.setAttribute("memberId", memberId);

        return true;
    }

    private void guestRequestCheck(HttpServletRequest request, Long memberId) throws AccessDeniedException {
        List<String> accessibleURIList = List.of(
                "/v1/search",
                "/v1/artist",
                "/v1/album",
                "/v1/track",
                "/v1/weekly-recommendation"
        );

        if (!memberId.equals(guestId)) {
            return;
        }

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/v1/artist/like")) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        for (String accessibleURI : accessibleURIList) {
            if (requestURI.startsWith(accessibleURI)) {
                return;
            }
        }

        throw new AccessDeniedException("접근 권한이 없습니다.");
    }
}
