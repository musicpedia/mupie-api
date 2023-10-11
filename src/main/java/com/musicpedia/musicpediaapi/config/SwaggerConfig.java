package com.musicpedia.musicpediaapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SwaggerConfig.java
@OpenAPIDefinition(
        info = @Info(title = "Mupie API 명세서",
                description = "음악 평점 앱 Mupie의 API 명세서입니다.",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
        @Bean
        public GroupedOpenApi mupieOpenApi() {
                String[] paths = {"/v1/**"};

                return GroupedOpenApi.builder()
                        .group("Mupie API v1")
                        .pathsToMatch(paths)
                        .build();
        }
}