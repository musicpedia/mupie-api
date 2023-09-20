package com.musicpedia.musicpediaapi.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfo {
    private String id;
    private String email;
    private String name;
    private String profile_image;
    private String description;
}
