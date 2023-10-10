package com.musicpedia.musicpediaapi.domain.rating.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RatingPage {
    private List<RatingDetail> ratings;

    private int totalPages;

    private long totalCount;

    private int page;

    private int size;

    private boolean hasNext;

    private boolean hasPrevious;

    private boolean isFirstPage;

    private boolean isLastPage;

    public static RatingPage from(Page<Rating> ratings) {
        List<RatingDetail> ratingDetails = ratings.getContent().stream()
                .map(Rating::toRatingDetail)
                .toList();

        return RatingPage.builder()
                .ratings(ratingDetails)
                .totalPages(ratings.getTotalPages())
                .totalCount(ratings.getTotalElements())
                .page(ratings.getNumber())
                .size(ratings.getSize())
                .hasNext(ratings.hasNext())
                .hasPrevious(ratings.hasPrevious())
                .isFirstPage(ratings.isFirst())
                .isLastPage(ratings.isLast())
                .build();
    }
}
