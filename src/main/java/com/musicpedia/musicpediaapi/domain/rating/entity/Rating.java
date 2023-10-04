package com.musicpedia.musicpediaapi.domain.rating.entity;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rating")
public class Rating extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Rating(
            String type,
            String spotifyId,
            Member member
    ) {
        this.type = type;
        this.spotifyId = spotifyId;
        this.member = member;
    }
}
