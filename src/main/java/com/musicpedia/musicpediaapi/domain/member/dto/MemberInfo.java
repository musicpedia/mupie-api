package com.musicpedia.musicpediaapi.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberInfo {
    private long id;

    private String email;

    private String name;

    private String profileImage;

    private String description;
}
