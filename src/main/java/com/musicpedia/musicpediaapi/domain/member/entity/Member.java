package com.musicpedia.musicpediaapi.domain.member.entity;

import com.musicpedia.musicpediaapi.domain.member.dto.MemberDetail;
import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@SQLDelete(sql = "UPDATE MEMBER SET deleted=true where id=?")
@Where(clause = "deleted is false")
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

    @Column(name = "deleted")
    private boolean deleted = false;

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

    public MemberDetail toMemberInfo() {
        return MemberDetail.builder()
                .email(this.email)
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .profileImage(this.profileImage)
                .build();
    }
}
