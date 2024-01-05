package com.musicpedia.musicpediaapi.domain.rating.repository;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.rating.dto.AverageScoreDTO;
import com.musicpedia.musicpediaapi.domain.rating.dto.RatingScoreDTO;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findBySpotifyIdAndMember(String spotifyId, Member member);

    Page<Rating> findAllByMemberAndType(Member member, Type type, Pageable pageable);

    long countAllByMemberAndType(Member member, Type type);

    @Query(value = "SELECT ROUND(AVG(CAST(r.score AS DOUBLE)),1) FROM rating r WHERE r.spotify_id = :spotifyId and r.deleted=false", nativeQuery = true)
    Double calculateAverageScoreBySpotifyId(@Param("spotifyId") String spotifyId);

    @Query(value = "SELECT r.spotify_id as spotifyId, r.score as ratingScore FROM rating r WHERE r.member_id = :memberId and r.spotify_id in :spotify_ids and r.deleted=false GROUP BY r.spotify_id", nativeQuery = true)
    List<RatingScoreDTO> calculateRatingScoresBySpotifyIds(@Param("memberId") Long memberId, @Param("spotify_ids") List<String> spotifyIds);

    @Query(value = "SELECT r.spotify_id as spotifyId, ROUND(AVG(CAST(r.score AS DOUBLE)),1) as averageScore FROM rating r WHERE r.spotify_id in :spotifyIds and r.deleted=false GROUP BY r.spotify_id", nativeQuery = true)
    List<AverageScoreDTO> calculateAverageScoresBySpotifyIds(@Param("spotifyIds") List<String> spotifyIds);
}
