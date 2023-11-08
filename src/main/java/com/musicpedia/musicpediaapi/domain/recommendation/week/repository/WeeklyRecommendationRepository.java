package com.musicpedia.musicpediaapi.domain.recommendation.week.repository;

import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyRecommendationRepository extends JpaRepository<WeeklyRecommendation, Long> {
    List<WeeklyRecommendation> findAllByType(Type type);
}
