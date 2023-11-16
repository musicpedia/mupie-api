package com.musicpedia.musicpediaapi.domain.recommendation.week.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @Builder
    public WeeklyRecommendation(
            String spotifyId
    ) {
        this.spotifyId = spotifyId;
    }
}
