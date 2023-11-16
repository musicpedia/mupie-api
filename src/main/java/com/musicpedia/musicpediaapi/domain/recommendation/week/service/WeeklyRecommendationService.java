package com.musicpedia.musicpediaapi.domain.recommendation.week.service;

import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationDetail;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationResponse;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeeklyRecommendationService {
    private final WeeklyRecommendationRepository weeklyRecommendationRepository;

    private List<String> weeklyRecommendationTrackIds;

    @PostConstruct
    public void init() {
        System.out.println("이 주의 추천 곡 id를 가져옵니다.");
        this.weeklyRecommendationTrackIds = findWeeklyRecommendationTrackIds();
        System.out.println("id: " + weeklyRecommendationTrackIds.get(0));
    }

    @Scheduled(cron = "0 3 0 * * MON") // 매주 월요일 자정에 실행
    public void updateWeeklyRecommendationTrackIds() {
        this.weeklyRecommendationTrackIds = findWeeklyRecommendationTrackIds();
    }

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

    private List<String> findWeeklyRecommendationTrackIds() {
        return weeklyRecommendationRepository.findAllByType(Type.TRACK)
                .stream()
                .map(WeeklyRecommendation::getSpotifyId)
                .toList();
    }

    public List<String> getWeeklyRecommendationTrackIds() {
        return weeklyRecommendationTrackIds;
    }
}
