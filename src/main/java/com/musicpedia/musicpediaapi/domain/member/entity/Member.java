package com.musicpedia.musicpediaapi.domain.member.entity;

import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    private OAuthInfo oauthInfo;

    @Builder
    public Member(
            String email,
            String nickname,
            String profileImage,
            String description,
            OAuthInfo oauthInfo
    ) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.description = description;
        this.oauthInfo = oauthInfo;
    }}
