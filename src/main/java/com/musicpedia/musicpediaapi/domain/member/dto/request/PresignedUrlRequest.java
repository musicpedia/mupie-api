package com.musicpedia.musicpediaapi.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PresignedUrlRequest {
    @Schema(example = "profile")
    @NotBlank
    private String category;

    @Schema(example = "myimage.jpg")
    @NotBlank
    private String fileName;
}
