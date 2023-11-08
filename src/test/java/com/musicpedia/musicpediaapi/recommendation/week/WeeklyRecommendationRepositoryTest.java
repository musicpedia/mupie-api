package com.musicpedia.musicpediaapi.recommendation.week;

import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.recommendation.week.entity.WeeklyRecommendation;
import com.musicpedia.musicpediaapi.domain.recommendation.week.repository.WeeklyRecommendationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeeklyRecommendationRepositoryTest {
    @Autowired
    WeeklyRecommendationRepository weeklyRecommendationRepository;

    @Test
    @DisplayName("[Repository] 이 주의 추천 곡 조회 - 성공")
    public void 이_주의_추천_곡_조회_성공() {
        // given
        Type type = Type.TRACK;

        // when
        List<WeeklyRecommendation> weeklyRecommendations = weeklyRecommendationRepository.findAllByType(type);
        WeeklyRecommendation weeklyRecommendation = weeklyRecommendations.get(0);

        // then
        assertThat(weeklyRecommendation.getType())
                .isEqualTo(Type.TRACK);
    }

}
