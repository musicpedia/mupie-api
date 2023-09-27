package com.musicpedia.musicpediaapi.domain.member.entity;

import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

}
