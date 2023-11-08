package com.musicpedia.musicpediaapi.domain.recommendation.week.entity;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "weekly_recommendation")
public class WeeklyRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    public WeeklyRecommendationDetail toWeeklyRecommendationDetail() {
        return WeeklyRecommendationDetail.builder()
                .spotifyId(this.spotifyId)
                .name(this.name)
                .artist(this.artist)
                .thumbnail(this.thumbnail)
                .type(this.type)
                .build();
    }
}
