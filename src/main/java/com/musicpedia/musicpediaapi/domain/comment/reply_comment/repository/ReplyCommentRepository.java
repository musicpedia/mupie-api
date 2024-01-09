package com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
    List<ReplyComment> findAllBySpotifyIdAndMember(String spotifyId, Member member);

    @Query("SELECT rc FROM ReplyComment rc JOIN FETCH rc.member WHERE rc.comment = :comment")
    Page<ReplyComment> findAllByComment(@Param("comment") Comment comment, Pageable pageable);

    Optional<ReplyComment> findByIdAndMember(long replyCommentId, Member member);
}
