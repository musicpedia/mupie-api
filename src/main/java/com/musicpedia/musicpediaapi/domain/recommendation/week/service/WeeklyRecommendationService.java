package com.musicpedia.musicpediaapi.domain.recommendation.week.service;

import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationResponse;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationTrack;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import com.musicpedia.musicpediaapi.domain.track.service.TrackService;
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

    private final TrackService trackService;
    private final RatingService ratingService;

    private List<String> weeklyRecommendationTrackIds;

    @PostConstruct
    public void init() {
        this.weeklyRecommendationTrackIds = findWeeklyRecommendationTrackIds();
    }

    @Scheduled(cron = "0 3 0 * * MON") // 매주 월요일 자정에 실행
    public void updateWeeklyRecommendationTrackIds() {
        this.weeklyRecommendationTrackIds = findWeeklyRecommendationTrackIds();
    }

    public WeeklyRecommendationResponse getWeeklyRecommendation(long memberId) {
        List<WeeklyRecommendationTrack> weeklyRecommendationTracks = weeklyRecommendationTrackIds
                .stream()
                .map(spotifyId -> WeeklyRecommendationTrack.builder()
                        .spotifyTrack(trackService.getTrack(memberId, spotifyId))
                        .score(ratingService.getScore(memberId, spotifyId))
                        .build()
                )
                .toList();

        int size = weeklyRecommendationTracks.size();

        return WeeklyRecommendationResponse.builder()
                .weeklyRecommendationTracks(weeklyRecommendationTracks)
                .size(size)
                .build();
    }

    private List<String> findWeeklyRecommendationTrackIds() {
        return weeklyRecommendationRepository.findAll()
                .stream()
                .map(WeeklyRecommendation::getSpotifyId)
                .toList();
    }

    public List<String> getWeeklyRecommendationTrackIds() {
        return weeklyRecommendationTrackIds;
    }
}
