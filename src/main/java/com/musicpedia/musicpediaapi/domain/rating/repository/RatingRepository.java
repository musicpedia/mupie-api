package com.musicpedia.musicpediaapi.domain.rating.repository;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findById(Long id);

    Optional<Rating> findBySpotifyIdAndMember(String spotifyId, Member member);
}
