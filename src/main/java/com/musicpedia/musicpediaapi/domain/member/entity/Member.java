package com.musicpedia.musicpediaapi.domain.member.entity;

import com.musicpedia.musicpediaapi.domain.member.dto.MemberInfo;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "description")
    private String description;

    @Column(name = "spotify_access_token")
    private String spotifyAccessToken;

    @Embedded
    private OAuthInfo oauthInfo;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @Builder
    public Member(
            String email,
            String name,
            String profileImage,
            String description,
            OAuthInfo oauthInfo
    ) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.description = description;
        this.oauthInfo = oauthInfo;
    }

    public void refreshAccessToken(String accessToken) {
        this.spotifyAccessToken = accessToken;
    }

    public MemberInfo toMemberInfo() {
        return MemberInfo.builder()
                .email(this.email)
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .profileImage(this.profileImage)
                .build();
    }
}
