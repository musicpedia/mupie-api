package com.musicpedia.musicpediaapi.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberUpdateRequest {
    @Schema(example = "곽의준")
    @NotBlank
    private String name;

    @Schema(example = "http://k.kakaocdn.net/dn/LOYlB/btss3FPc6O7/OhLsCdeSkkjKSGnjnXDlvK/1234.jpg")
    private String profileImage;

    @Schema(example = "안녕하세요 발라드를 좋아하는 뜨거운 남자입니다.")
    private String description;
}
