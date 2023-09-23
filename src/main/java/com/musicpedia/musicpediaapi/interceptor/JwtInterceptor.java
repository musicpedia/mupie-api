package com.musicpedia.musicpediaapi.interceptor;

import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Preflight인 경우 허용
        if(CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            throw new IllegalArgumentException("필수 헤더값이 없습니다.");
        }
        String token = jwtUtil.resolveToken(bearerToken);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        // 추출한 멤버 ID를 요청 속성(attribute)에 저장하여 컨트롤러에서 사용할 수 있도록 함
        request.setAttribute("memberId", memberId);

        return true;
    }
}
