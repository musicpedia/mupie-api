package com.musicpedia.musicpediaapi.domain.rating.repository;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findBySpotifyIdAndMember(String spotifyId, Member member);

    Page<Rating> findAllByMemberAndType(Member member, Type type, Pageable pageable);

    long countAllByMemberAndType(Member member, Type type);

    @Query(value = "SELECT ROUND(AVG(CAST(r.score AS FLOAT)),1) FROM Rating r WHERE r.spotify_id = :spotify_id", nativeQuery = true)
    Float calculateAverageScoreBySpotifyId(@Param("spotify_id") String spotifyId);
}
