package com.musicpedia.musicpediaapi.domain.recommendation.week.service;

import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationDetail;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationResponse;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeeklyRecommendationService {
    private final WeeklyRecommendationRepository weeklyRecommendationRepository;

    public WeeklyRecommendationResponse getWeeklyRecommendation(Type type) {
        List<WeeklyRecommendationDetail> weeklyRecommendations = weeklyRecommendationRepository.findAllByType(type)
                .stream()
                .map(WeeklyRecommendation::toWeeklyRecommendationDetail)
                .toList();
        int size = weeklyRecommendations.size();

        return WeeklyRecommendationResponse.builder()
                .weeklyRecommendations(weeklyRecommendations)
                .size(size)
                .build();
    }
}
