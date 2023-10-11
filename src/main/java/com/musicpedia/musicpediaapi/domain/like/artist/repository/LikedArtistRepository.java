package com.musicpedia.musicpediaapi.domain.like.artist.repository;

import com.musicpedia.musicpediaapi.domain.like.artist.entity.LikedArtist;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikedArtistRepository extends JpaRepository<LikedArtist, Long> {
    Optional<LikedArtist> findBySpotifyIdAndMember(@Param("spotifyId") String spotifyId, @Param("member") Member member);

    Page<LikedArtist> findAllByMember(Member member, Pageable pageable);

    long countAllByMember(Member member);
}
