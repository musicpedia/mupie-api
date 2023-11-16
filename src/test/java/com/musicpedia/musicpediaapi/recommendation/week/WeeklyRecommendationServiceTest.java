package com.musicpedia.musicpediaapi.recommendation.week;

import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationResponse;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import com.musicpedia.musicpediaapi.domain.recommendation.week.service.WeeklyRecommendationService;
import com.musicpedia.musicpediaapi.domain.track.dto.SpotifyTrack;
import com.musicpedia.musicpediaapi.domain.track.service.TrackService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class WeeklyRecommendationServiceTest {
    @Mock
    private WeeklyRecommendationRepository weeklyRecommendationRepository;
    @Mock
    private TrackService trackService;

    @InjectMocks
    private WeeklyRecommendationService weeklyRecommendationService;

    @Test
    @DisplayName("[Service] 이 주의 추천 곡 조회 - 성공")
    public void 이_주의_추천_곡_조회_성공() {
        //given
        List<WeeklyRecommendation> testWeeklyRecommendations = testWeeklyRecommendationBuilder();
        SpotifyTrack spotifyTrack = testSpotifyTrack();
        given(weeklyRecommendationRepository.findAll())
                .willReturn(testWeeklyRecommendations);
        given(trackService.getTrack(anyLong(), anyString()))
                .willReturn(spotifyTrack);

        //when
        WeeklyRecommendationResponse response = weeklyRecommendationService.getWeeklyRecommendation(1L);
        List<SpotifyTrack> weeklyRecommendations = response.getWeeklyRecommendations();
        int size = response.getSize();

        // then
        assertNotNull(weeklyRecommendations);
        assertThat(size)
                .isEqualTo(2);
    }

    private List<WeeklyRecommendation> testWeeklyRecommendationBuilder() {
        WeeklyRecommendation track1 = WeeklyRecommendation.builder()
                .spotifyId("track_1_ID")
                .build();
        WeeklyRecommendation track2 = WeeklyRecommendation.builder()
                .spotifyId("track_2_ID")
                .build();
        return List.of(track1, track2);
    }

    private SpotifyTrack testSpotifyTrack() {
        return new SpotifyTrack();
    }
}
