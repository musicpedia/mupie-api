package com.musicpedia.musicpediaapi.domain.comment.comment.repository;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndMember(Long id, Member member);

    Optional<Comment> findByIdAndSpotifyId(Long id, String spotifyId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.member WHERE c.spotifyId = :spotifyId")
    Page<Comment> findAllBySpotifyId(@Param("spotifyId") String spotifyId, Pageable pageable);

    List<Comment> findAllBySpotifyIdAndMember(String spotifyId, Member member);
}
