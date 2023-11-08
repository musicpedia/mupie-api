package com.musicpedia.musicpediaapi.recommendation.week;

import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationDetail;
import com.musicpedia.musicpediaapi.domain.recommendation.week.dto.WeeklyRecommendationResponse;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import com.musicpedia.musicpediaapi.domain.recommendation.week.service.WeeklyRecommendationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class WeeklyRecommendationServiceTest {
    @Mock
    private WeeklyRecommendationRepository weeklyRecommendationRepository;

    @InjectMocks
    private WeeklyRecommendationService weeklyRecommendationService;

    @Test
    @DisplayName("[Service] 이 주의 추천 곡 조회 - 성공")
    public void 이_주의_추천_곡_조회_성공() {
        //given
        List<WeeklyRecommendation> weeklyRecommendations = testWeeklyRecommendationBuilder();
        given(weeklyRecommendationRepository.findAllByType(any()))
                .willReturn(weeklyRecommendations);

        //when
        WeeklyRecommendationResponse response = weeklyRecommendationService.getWeeklyRecommendation(Type.TRACK);
        List<WeeklyRecommendationDetail> weeklyRecommendationDetails = response.getWeeklyRecommendations();
        int size = response.getSize();

        //then
        assertThat(weeklyRecommendationDetails.get(1).getName())
                .isEqualTo("부여 남궁지에서");
        assertThat(size)
                .isEqualTo(2);
    }

    private List<WeeklyRecommendation> testWeeklyRecommendationBuilder() {
        WeeklyRecommendation track1 = WeeklyRecommendation.builder()
                .name("sunflower")
                .spotifyId("track1")
                .artist("post malone")
                .type(Type.TRACK)
                .thumbnail("sunflower thumbnail")
                .build();
        WeeklyRecommendation track2 = WeeklyRecommendation.builder()
                .name("부여 남궁지에서")
                .spotifyId("track2")
                .artist("곽의준")
                .type(Type.TRACK)
                .thumbnail("buyeo thumbnail")
                .build();
        return List.of(track1, track2);
    }
}
