package com.musicpedia.musicpediaapi.domain.comment.comment.repository;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllBySpotifyId(String spotifyId, Pageable pageable);
}
