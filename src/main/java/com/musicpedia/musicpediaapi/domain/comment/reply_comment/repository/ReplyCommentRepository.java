package com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository;

import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
    List<ReplyComment> findAllBySpotifyIdAndMember(String spotifyId, Member member);
}
