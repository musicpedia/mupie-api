package com.musicpedia.musicpediaapi.domain.artist.liked_artist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.musicpedia.musicpediaapi.domain.artist.liked_artist.entity.LikedArtist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikedArtistPage {
    private List<LikedArtistDetail> artists;

    @Schema(example = "13")
    private int totalPages;

    @Schema(example = "183")
    private long totalCount;

    @Schema(example = "2")
    private int page;

    @Schema(example = "20")
    private int size;

    private boolean hasNext;

    private boolean hasPrevious;

    private boolean isFirstPage;

    private boolean isLastPage;

    public static LikedArtistPage from(Page<LikedArtist> likedArtists) {
        List<LikedArtistDetail> likedArtistDetails = likedArtists.getContent().stream()
                .map(LikedArtist::toLikedArtistDetail)
                .toList();

        return LikedArtistPage.builder()
                .artists(likedArtistDetails)
                .totalPages(likedArtists.getTotalPages())
                .totalCount(likedArtists.getTotalElements())
                .page(likedArtists.getNumber())
                .size(likedArtists.getSize())
                .hasNext(likedArtists.hasNext())
                .hasPrevious(likedArtists.hasPrevious())
                .isFirstPage(likedArtists.isFirst())
                .isLastPage(likedArtists.isLast())
                .build();
    }
}
