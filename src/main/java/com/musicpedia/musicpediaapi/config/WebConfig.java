package com.musicpedia.musicpediaapi.config;

import com.musicpedia.musicpediaapi.global.util.JwtUtil;
import com.musicpedia.musicpediaapi.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;

    @Value("${guest.member-id}")
    private long guestId;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // 허용할 출처
                .allowedMethods("GET","POST","PUT","DELETE") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(jwtUtil, guestId))
                .addPathPatterns("/v1/**")
                .excludePathPatterns("/v1/auth/**");
    }
}
